package com.yc.spring.context;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.yc.spring.annotation.Component;
import com.yc.spring.annotation.Controller;
import com.yc.spring.annotation.Service;

public class AnnotationApplicationContext {
	public Map<String, Object> beanMap = new HashMap<String, Object>();

	public AnnotationApplicationContext() {
		String pkg = "com.yc.project";
		// 包的扫描
		scanPackage(pkg);
	}

	// 包的扫描
	private void scanPackage(final String pkg) {
		// 包名换成路径表示
		String pkgDir = pkg.replaceAll("\\.", "/");
		URL url = getClass().getClassLoader().getResource(pkgDir);
		File file = new File(url.getFile());
		// 筛选将.class文件添加数组中， 文件过滤器
		File[] files = file.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				String fileName = pathname.getName();
				if (pathname.isDirectory()) {// 子包 继续对子包进行扫描
					scanPackage(pkg + "." + fileName);
				} else {
					// 判断文件的后缀名.class
					if (fileName.endsWith(".class")) {
						return true;
					}
				}
				return false;
			}
		});
		// 循环满足条件的所有字节码文件
		for (File f : files) {
			String fileName = f.getName();
			// 取出.class后缀名
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			// 将名字的第一-个字母转为小写Student student
			String key = String.valueOf(fileName.charAt(0)).toLowerCase() + fileName.substring(1);
			// 构架类的全路径名称
			String pkgClass = pkg + "." + fileName;
			// 反射构建对象
			try {
				Class<?> c = Class.forName(pkgClass);
				// 判断类上是否使用了注解
				if (c.isAnnotationPresent(Controller.class) || c.isAnnotationPresent(Service.class)
						|| c.isAnnotationPresent(Component.class)) {
					Object obj = c.newInstance();
					// 将对象设置到map中
					beanMap.put(key, obj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Object getBean(String key) {
		return beanMap.get(key);
	}

	public void close() {
		beanMap.clear();
		beanMap = null;
	}
}
