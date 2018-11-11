package southday.java.pattern.proxy.protect;

import java.lang.reflect.Proxy;

public class MatchMakingTestDrive {
    public static void main(String[] args) {
        MatchMakingTestDrive test = new MatchMakingTestDrive();
        test.drive();
    }
    
    public void drive() {
        PersonBean joe = getPersonFromDatabase("Joe Javabean");
        PersonBean ownerProxy = getOwnerProxy(joe);
        System.out.println("Name is: " + ownerProxy.getName());
        ownerProxy.setInterests("bowling, Go");
        System.out.println("Interests set from owner proxy");
        try {
            ownerProxy.setHotOrNotRating(10);
        } catch (Exception e) {
            System.out.println("Can't set rating from owner proxy");
        }
        System.out.println("Rating is: " + ownerProxy.getHotOrNotRating());
        
        PersonBean nonOwnerProxy = getNonOwnerProxy(joe);
        System.out.println("Name is: " + nonOwnerProxy.getName());
        try {
            nonOwnerProxy.setInterests("bowling, Go");
        } catch (Exception e) {
            System.out.println("Can't set interests from non owner proxy");
        }
        nonOwnerProxy.setHotOrNotRating(3);
        System.out.println("Rating set from non owner proxy");
        System.out.println("Rating is: " + nonOwnerProxy.getHotOrNotRating());
    }
    
    // 模拟从数据库获取用户对象
    private PersonBean getPersonFromDatabase(String name) {
        PersonBean person = new PersonBeanImpl();
        person.setName(name);
        person.setGender("man");
        person.setInterests("basketball, football, swimming");
        person.setHotOrNotRating(1);
        return person;
    }
    
    private PersonBean getOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(
                person.getClass().getClassLoader(), 
                person.getClass().getInterfaces(),
                new OwnerInvocationHandler(person));
                
    }
    
    private PersonBean getNonOwnerProxy(PersonBean person) {
        return (PersonBean) Proxy.newProxyInstance(
                person.getClass().getClassLoader(),
                person.getClass().getInterfaces(),
                new NonOwnerInvocationHandler(person));
    }
}
