package com.yc.project;

import com.yc.project.biz.UserBiz;
import com.yc.project.controller.UserController;
import com.yc.spring.context.AnnotationApplicationContext;

public class Main {
	public static void main(String[] args) {
		AnnotationApplicationContext ctx = new AnnotationApplicationContext();
		UserController controller = (UserController) ctx.getBean("userController");
		System.out.println(controller);
		controller.show();
		UserBiz biz = (UserBiz) ctx.getBean("userBiz");
		biz.find();
	}
}
