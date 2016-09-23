package inv_java;

import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import inv_java.tools.Http;
import inv_java.weixin.Message;

public class Manager {
	
	public static void start(String[] args) {
		if (!app.init(args)) {
            return;
		}
		
//		startTimer();

		System.out.println("abcc");
		try {
			long time;
			int count = 100000;

			{
				time = System.currentTimeMillis();
				for (int i = 0; i < count; i++) {
					int a = 1 + 2;
				}
				System.out.println("java: " + (System.currentTimeMillis() - time) / 1000.0);
			}

			{
				ScriptEngineManager engineManager = new ScriptEngineManager();
				ScriptEngine engine = engineManager.getEngineByName("nashorn");
				System.out.println(engine.eval("1+2;"));

				time = System.currentTimeMillis();
				for (int i = 0; i < count; i++) {
					Object o = engine.eval("1+2;");
				}
				System.out.println("nashorn: " + (System.currentTimeMillis() - time) / 1000.0);
			}
			
		} catch (Exception e) {
			app.logError(e, "js error");
		}
		System.out.println("abcc2");
	}
	
	public static void startTimer() {
		app.scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				onTick();
			}
		}, 1, 100000, TimeUnit.SECONDS);
		app.log("start timer");
	}
	
	public static void onTick() {
		sendRequest();
	}
	
	public static void sendRequest() {
        String body = new Http("http://hq.sinajs.cn/list=sz131810").getBody();
		new Message(body).send();
	}
	
	

}