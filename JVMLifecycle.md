HotSpot VM 启动时JNI_CreateJavaVM方法将执行以下一系列操作。
（1）确保只有一个线程调用这个方法并且确保只创建一个HotSpot VM实例。因为HotSpot VM创建的静态数据结构无法再次初始化，所以一旦初始化到达某个确定点后，进程空间里就只能有一个HotSpot VM，在HotSpot VM的开发工程师看来，HotSpot VM启动至此已经是无法逆转了。
（2）检查并确保支持当前的JNI版本，初始化垃圾收集日志的输出流。
（3）初始化OS模块，如随机数生成器（Random Number Generator）、当前进程id（Current Process id）、高精度计时器（High-Resolution Timer）、内存页尺寸（Memory Page Sizes）、保护页（Guard Pages）。保护页是不可访问的内存页，用作内存访问区域的边界。例如，操作系统常在线程栈顶压入一个保护页以保证引用不会超出栈的边界。
（4）解析传入JNI_CreateJavaVM的命令行选项，保存以备将来使用。
（5）初始化标准的Java系统属性，例如java.version、java.vendor、os.name等。
（6）初始化支持同步、栈、内存和安全点页的模块。
（7）加载libzip、libhpi、libjava及libthread等库。
（8）初始化并设置信号处理器（Signal Handler）。
（9）初始化线程库。
（10）初始化输出流日志记录器（Logger）。
（11）如果用到Agent库（hprof、jdi），则初始化并启动。
（12）初始化线程状态（Thread State）和线程本地存储（Thread Local Storage），它们存储了线程私有数据。
（13）初始化部分HotSpot VM全局数据，例如事件日志（Event Log），OS同步原语、perfMemory（性能统计数据内存），以及chunkPool（内存分配器）。
（14）至此，HotSpot VM可以创建线程了。创建出来的Java版main线程被关联到当前操作系统线程，只不过还没有添加到已知线程列表中。
（15）初始化并**Java级别的同步。
（16）初始化启动类加载器（Bootclassloader）、代码缓存、解释器、JIT编译器、JNI、系统词典（System Dictionary）及universe（一种必备的全局数据结构集）。
（17）现在，添加Java主线程到已知线程列表中。检查universe是否正常。创建HotSpot VMThread，它执行HotSpot VM所有的关键功能。同事发出适当的JVMTI事件，报告HotSpot VM当前的状态。
（18）加载和初始化以下Java类：java.lang.String、java.lang.System、java.lang.Thread、java.lang.ThreadGroup、java.lang.reflect.Method、java.lang.ref.Finalizer、java.lang.Class以及余下的Java系统类。此时，HotSpot已经初始化完毕并可使用，只是功能还不完备。
（19）启动HotSpot VM的信号处理器线程，初始化JIT编译器并启动HotSpot编译代理线程。启动HotSpot VM辅助线程（如监控线程和统计抽样器）。至此，HotSpot VM已功能完备。
（20）最后，生成JNIEnv对象返回给调用者，HotSpot则准备响应新的JNI请求。

如果HotSpot VM启动过程中发生错误，启动器则调用DestroyJavaVM方法关闭HotSpot VM。如果HotSpot VM启动后执行过程中发生很严重的错误，也会调用DestroyJavaVM方法。
DestroyJavaVM按以下步骤停止HotSpot VM。
（1）一直等待，直到只有一个非守护的线程执行，注意此时HotSpot VM仍然可用。
（2）调用java.lang.Shutdown.shutdown()，它会调用Java上的shutdown钩子方法，如果finalization-on-exit为true，则运行Java对象的finalizer。
（3）运行HotSpot VM上的shutdown钩子（通过JVM_OnExit()注册），停止以下线程：性能分线器、统计数据抽样器、监控线程及垃圾收集器线程。发出状态事件通知JVMTI，然后关闭JVMTI、停止信号线程。
（4）调用HotSpot的JavaThread::exit()释放JNI处理块，移除保护页，并将当前线程从已知线程队列中移除。从这时起，HotSpot VM就无法执行任何Java代码了。
（5）停止HotSpot VM线程，将遗留的HotSpot VM线程带到安全点并停止JIT编译器线程。
（6）停止追踪JNI，HotSpot VM及JVMTI屏障。
（7）为哪些仍然以本地代码运行的线程设置标记“vm exited”。
（8）删除当前线程。
（9）删除或移除所有的输入/输出流，释放PerfMemory（性能统计内存）资源。
（10）最后返回到调用者。
