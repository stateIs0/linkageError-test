package org.example;


/**
 * @version 1.0
 * @Author cxs
 * @Description
 * @date 2023/8/21
 **/
public class HelloImplSon implements HelloInterface {

    /**
     *  这个 Delegate 在 b 模块里是没有 shade 的.
     *  此时如果 HelloImplB 先加载, 那么 HelloInterface 也会被 b cl 加载;
     *  当调用 hello 时, Delegate.get() 则会执行;
     *  而 Delegate 只存在 a jar 里, 由于打破了双亲委派, Delegate 也需要加载 HelloInterface, 那就由 a cl 加载;
     *  当 a 加载 HelloInterface, 此时, 会触发 java.lang.LinkageError: loader constraint violation;
     *  因为 b 已经加载过 HelloInterface 了;
     *  注意, 这种去重检查, 只存在于 父子关系的 classloader, 对于平行的 classloader, 则不会出现.
     *  比如 2 个 a classloader 实例, 先后加载 HelloInterface, 是不会触发此错误的.
     *
     *  简单来讲, 就是一个 class 里
     */

    @Override
    public HelloModel hello() {
        try {
            return Delegate.get().hello();
        } catch (Error error) {
            error.printStackTrace();
            return null;
        }
    }

    @Override
    public void hi(HelloModel model) {
        //helloInterface.hello();
        Delegate.get().hi(model);
    }
}
