package com.sjw.basic;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

public class AnnotationTest {

	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
		AnnoTestableProcessor.process("com.sjw.basic.MyTest");
	}

}

//自定义标注
//使用JDK的元数据Annotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface AnnoTestable{
	
}

//使用自定义标注的类
@Assignment(assignee = "sjw", effort = 1)
class MyTest{
	@AnnoTestable
	public void test1(){
		System.out.println("test1");
	}
	void test2(){
		System.out.println("test2");
	}
}

class AnnoTestableProcessor{
	public static void process(String clazz) throws ClassNotFoundException,NoSuchMethodException{
		Annotation[] annoArr = Class.forName(clazz).getMethod("test1").getAnnotations();//提取Annotation
		for (int i = 0; i < annoArr.length; i++) {
			System.out.println(annoArr[i]);
		}
		for(Method m:Class.forName(clazz).getMethods()){
			if (m.isAnnotationPresent(AnnoTestable.class)) {//判断是否有指定标注
				try {
					m.invoke(new MyTest());//调用方法
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
}

/**
 * APT
 * JDK 5中的apt工具的不足之处在于它是Oracle提供的私有实现。在JDK 6中，通过JSR 269把自定义注解处理器这一功能进行了规范化，有了新的javax.annotation.processing这个新的API。对Mirror API也进行了更新，形成了新的javax.lang.model包。注解处理器的使用也进行了简化，不需要再单独运行apt这样的命令行工具，Java编译器本身就可以完成对注解的处理。
 *在这里可以看到@Retention和@Target这样的元注解，用来声明注解本身的行为。@Retention用来声明注解的保留策略，有CLASS、RUNTIME和SOURCE这三种，分别表示注解保存在类文件、JVM运行时刻和源代码中。只有当声明为RUNTIME的时候，才能够在运行时刻通过反射API来获取到注解的信息。@Target用来声明注解可以被添加在哪些类型的元素上，如类型、方法和域等。
 *javac -processor com.sjw.basic.AssignmentProcess MyTest.java
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Assignment {
    String assignee();
    int effort();
    double finished() default 0;
}

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes("com.sjw.basic.Assignment")
class AssignmentProcess extends AbstractProcessor 
{

	private TypeElement assignmentElement; 
	@Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        Elements elementUtils = processingEnv.getElementUtils();
        assignmentElement = elementUtils.getTypeElement("com.sjw.basic.Assignment");
    } 
	@Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(assignmentElement);
        for (Element element : elements) {
            processAssignment(element);
        }
        return true;
    }
    private void processAssignment(Element element) {
        List<? extends AnnotationMirror> annotations = element.getAnnotationMirrors();
        for (AnnotationMirror mirror : annotations) {
            if (mirror.getAnnotationType().asElement().equals(assignmentElement)) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
                String assignee = (String) getAnnotationValue(values, "assignee"); //获取注解的值
                }
        }
    } 
    private String getAnnotationValue(Map<? extends ExecutableElement, ? extends AnnotationValue> values,String annotationFiledName) {
		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : values.entrySet()) {
			if (entry.getKey().getSimpleName().contentEquals(annotationFiledName)) {
				return entry.getValue().getValue().toString();
			}
		}
		return null;
	} 


}