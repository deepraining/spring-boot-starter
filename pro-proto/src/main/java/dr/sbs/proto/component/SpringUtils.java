package dr.sbs.proto.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringUtils implements BeanFactoryPostProcessor {
  private static ConfigurableListableBeanFactory beanFactory;

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    SpringUtils.beanFactory = beanFactory;
  }

  public static ConfigurableListableBeanFactory getBeanFactory() {
    return beanFactory;
  }

  /**
   * 获取对象
   *
   * @param name bean名称
   * @return Object 一个以所给名字注册的bean的实例
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) throws BeansException {
    if (getBeanFactory() == null) {
      System.out.println("本地调试Main模式，没有BeanFactory，忽略错误");
      return null;
    } else {
      T result = (T) getBeanFactory().getBean(name);
      return result;
    }
  }

  /**
   * 获取类型为requiredType的对象
   *
   * @param name bean名称
   * @return bean
   */
  public static <T> T getBean(Class<T> name) throws BeansException {
    if (getBeanFactory() == null) {
      System.out.println("本地调试Main模式，没有BeanFactory，忽略错误");
      return null;
    } else {
      T result = (T) getBeanFactory().getBean(name);
      return result;
    }
  }

  /**
   * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
   *
   * @param name bean名称
   * @return boolean
   */
  public static boolean containsBean(String name) {
    return getBeanFactory().containsBean(name);
  }

  /**
   * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
   * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
   *
   * @param name bean名称
   * @return boolean
   */
  public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
    return getBeanFactory().isSingleton(name);
  }

  /**
   * @param name bean名称
   * @return Class 注册对象的类型
   */
  public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
    return getBeanFactory().getType(name);
  }

  /**
   * 如果给定的bean名字在bean定义中有别名，则返回这些别名
   *
   * @param name bean名称
   * @return list
   */
  public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
    return getBeanFactory().getAliases(name);
  }
}
