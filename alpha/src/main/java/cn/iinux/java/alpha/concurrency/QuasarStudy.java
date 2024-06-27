package cn.iinux.java.alpha.concurrency;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;

public class QuasarStudy {
    // -javaagent:quasar-core-0.7.4-jdk8.jar
    // https://www.cnblogs.com/jmcui/p/12511623.html
    public static void main(String[] args) {
        new Fiber<Void>() {
            @Override
            protected Void run() throws SuspendExecution, InterruptedException {
                System.out.println("Hello Fiber1");
                return null;
            }
        }.start();
        new Fiber<>(() -> {
            System.out.println("Hello Fiber2");
            return null;
        }).start();
        new Fiber<Void>(new SuspendableRunnable() {
            public void run() throws SuspendExecution, InterruptedException {
                System.out.println("Hello from fiber!");
            }
        }).start();
        final Channel<Integer> ch = Channels.newChannel(0);

        new Fiber<Void>(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("before send " + i);
                ch.send(i);
                System.out.println("after send " + i);
            }
        }).start();

        new Fiber<Void>(() -> {
            Integer x;
            while ((x = ch.receive()) != null)
                System.out.println("Received: " + x);
        }).start();
    }
}

/*
import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.fibers.SuspendExecution;

public class PingPongActor extends BasicActor<String> {
    protected String doRun() throws InterruptedException, SuspendExecution {
        for (;;) {
            String msg = receive();
            if (msg.equals("ping"))
                System.out.println("ping");
            else if (msg.equals("pong"))
                System.out.println("pong");
            else
                break;
        }
        return "done";
    }
}

import co.paralleluniverse.actors.BasicActor;
import co.paralleluniverse.fibers.SuspendExecution;

public class MyActor extends BasicActor<Message> {
    @Override
    protected Void doRun() throws InterruptedException, SuspendExecution {
        while (receive() != null) {
            // Process messages
        }
        return null;
    }
}

public class Message {
    public final String type;
    public final Object content;

    public Message(String type, Object content) {
        this.type = type;
        this.content = content;
    }
}

@Override
protected Void doRun() throws InterruptedException, SuspendExecution {
    Message msg;
    while ((msg = receive()) != null) {
        switch (msg.type) {
            case "greet":
                System.out.println("Hello, " + msg.content);
                break;
            case "quit":
                return null;
            default:
                System.out.println("Unknown message type");
        }
    }
    return null;
}

ActorRef<Message> actor = new MyActor().spawn();

actor.send(new Message("greet", "World"));

@Override
protected Void init() {
    // Initialization code
    return null;
}

@Override
protected void terminate(Throwable cause) {
    // Cleanup code
}

public class SupervisorActor extends BasicActor<Object> {
    @Override
    protected Void doRun() throws InterruptedException, SuspendExecution {
        ChildActor child = new ChildActor().spawn();

        try {
            for (;;) {
                Object msg = receive();
                child.send(msg);
            }
        } catch (Exception e) {
            // Handle exception, maybe restart the child
            child = new ChildActor().spawn();
        }
    }
}

ActorRef<Message> actor = new MyActor().spawn();
ActorRegistry.register("myActor", actor);

// Later, retrieve the actor
ActorRef<Message> retrievedActor = ActorRegistry.getActor("myActor");

Message msg = receive(1000); // Wait for 1 second
if (msg == null) {
    // Handle timeout
}

public class RequestResponseActor extends BasicActor<Object> {
    protected Integer doRun() throws SuspendExecution, InterruptedException {
        for (;;) {
            Object msg = receive();
            if (msg instanceof Integer) {
                int n = (Integer) msg;
                reply(n * 2);
            }
        }
    }
}

// Usage
ActorRef<Object> actor = new RequestResponseActor().spawn();
int result = (int) actor.request(5);
System.out.println("Result: " + result); // Output: Result: 10


 */