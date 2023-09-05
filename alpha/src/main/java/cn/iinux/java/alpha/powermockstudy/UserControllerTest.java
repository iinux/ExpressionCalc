package cn.iinux.java.alpha.powermockstudy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;
import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.support.membermodification.MemberMatcher.method;

@RunWith(PowerMockRunner.class)
@PrepareForTest({UserController.class, FileHelper.class})
// @PowerMockIgnore("javax.management.*")
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController uc = new UserController();

    @Test
    public void testAddUser() throws Exception {
        UserDto ud = new UserDto();
        when(userService.addUser(ud)).thenReturn(1);
        // can not stub like this
        // PowerMockito.doReturn(1).when(userService.addUser(ud));
        boolean result = uc.addUser(ud);
        assertEquals(result, true);
    }

    @Test
    public void testDelUser() throws Exception {
        int toDelete = 1;
        when(userService.delUser(toDelete)).thenThrow(new Exception("mock exception"));
        boolean result = uc.delUser(toDelete);
        assertEquals(result, false);
    }

    @Test
    public void mockFileHelper() {
        PowerMockito.mockStatic(FileHelper.class);
        // when(FileHelper.getName("lucy")).thenReturn("lily");
        when(FileHelper.getName(Matchers.anyString())).thenReturn("lily");
        assertEquals(FileHelper.getName("lucyx"), "lily");
    }

    @Test
    public void testSaveUser() throws Exception {
        UserDto userDto = new UserDto();

        // way one:
        PowerMockito.doNothing().when(userService, "saveUser", userDto);

        // way two:
        PowerMockito.doNothing().when(userService).saveUser(userDto);

        uc.saveUser(userDto);
    }

    @Test
    public void testModUser() throws Exception {
        UserDto ud = new UserDto();
        int moded = 1;

        when(userService.modUser(ud)).thenReturn(moded);

        UserController uc2 = mock(UserController.class);

        // 给没有 setter 方法的 私有字段 赋值。
        // 需要注意的是：此处的uc2是mock出来的，不是 UserControllerTest 类中的成员变量 uc
        Whitebox.setInternalState(uc2, "userService", userService);

        // 因为要测试的是 modUser() 方法，
        // 所以，当调用这个方法时，应该让它调用真实的方法，而非被mock掉的方法
        when(uc2.modUser(ud)).thenCallRealMethod();

        // 在modUser()方法中会调用verifyMod()这个私有方法，所以，需要将mock掉
        when(uc2, "verifyMod", moded).thenReturn(true);

        boolean result = uc2.modUser(ud);

        assertEquals(result, true);
    }

    @Test
    public void testModUser2() throws Exception {
        UserDto ud = new UserDto();
        int moded = 1;

        when(userService.modUser(ud)).thenReturn(moded);

        // 对uc进行监视
        uc = spy(uc);
        // 当uc的verifyMod被执行时，将被mock掉
        when(uc, "verifyMod", moded).thenReturn(true);
        boolean result = uc.modUser(ud);

        assertEquals(result, true);

        // 使用spy方法可以避免执行被测类中的成员函数，即mock掉不想被执行的私有方法。
    }

    @Test
    public void testVerifyMod() throws Exception { // 不依赖 @RunWith(PowerMockRunner.class)
        // 获取Method对象，
        Method method = method(UserController.class, "verifyMod", int.class);
        // 调用Method的invoke方法来执行
        boolean result = (boolean) method.invoke(uc, 1);
        assertEquals(result, true);
    }

    @Test
    public void testVerifyMod2() throws Exception { // 不依赖 @RunWith(PowerMockRunner.class)
        // 通过 Whitebox 来执行
        boolean result = Whitebox.invokeMethod(uc, "verifyMod", 1);
        assertEquals(result, true);
    }

    @Test
    public void testCountUser() throws Exception {
        UserDto ud = new UserDto();
        ud.setId(1);

        PowerMockito.whenNew(UserDto.class).withNoArguments().thenReturn(ud);

        int count = uc.countUser();

        assertEquals(count, 1);
    }

    @Test
    public void mockVoidStaticMethod() throws Exception {
        PowerMockito.mockStatic(FileHelper.class);
        PowerMockito.doNothing().when(FileHelper.class, "voidMethod", "mysql");
        FileHelper.voidMethod("mysql");
    }

    @Test
    public void mockGeneric() throws Exception {
        ArrayList<Integer> ai = new ArrayList<>();
        ai.add(1);

        PowerMockito.whenNew(ArrayList.class).withNoArguments().thenReturn(ai);

        PowerMockito.whenNew(ArrayList.class).withNoArguments().thenAnswer(new Answer<List<Integer>>() {
            @Override
            public List<Integer> answer(InvocationOnMock invocation) throws Throwable {
                ArrayList<Integer> ai = new ArrayList<>();
                ai.add(2);
                return ai;
            }
        });

        UserController uc = spy(new UserController());
        when(uc.getFlowByPrjId(anyInt(), (Integer[]) Matchers.anyVararg())).thenReturn(ai);
        when(uc.getFlowByPrjId0(any(), any())).thenReturn(ai);
        // 报 org.mockito.exceptions.misusing.InvalidUseOfMatchersException 需要有 spy()

        ArrayList<Integer> bi = new ArrayList<>();
        System.out.println(bi);

        System.out.println(uc.getFlowByPrjId(1, 1));
        System.out.println(uc.getFlowByPrjId0(1, 1));
    }

    @Test
    public void mockPrivateInnerClass() throws Exception {
        Class clazz = Whitebox.getInnerClassType(UserController.class, "InnerClassA");
        Constructor constructor = Whitebox.getConstructor(clazz, String.class);
        // the constructor needs a string parameter
        Object object = constructor.newInstance("mock name");
        // run the 'run' method
        Whitebox.invokeMethod(object, "run");
    }

    @Test
    public void testInit() throws Exception {
        DispatcherServlet ds = spy(new DispatcherServlet());

        // use "method()" to get the "init" method which is defined in "GenericServlet"
        // use "suppress()" to suppress the "init" method
        suppress(method(GenericServlet.class, "init", ServletConfig.class));

        System.out.println("start init");
        ds.init(mock(ServletConfig.class));
        // other to do ...
    }

    @Test
    public void test1() throws InterruptedException {
        UserController ns = new UserController();
        ns = spy(ns);

        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node());
        nodes.add(new Node());
        when(ns.getChildren(anyInt())).thenReturn(nodes);

        List<Node> result = new ArrayList<>();
        ns.getAllChildren(1, result);

        assertEquals(result.size(), 2);
        Thread.sleep(Long.MAX_VALUE);
    }

}
