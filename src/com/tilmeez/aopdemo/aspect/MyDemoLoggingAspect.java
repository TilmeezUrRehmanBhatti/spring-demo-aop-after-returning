package com.tilmeez.aopdemo.aspect;

import com.tilmeez.aopdemo.Account;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@Order(2)
public class MyDemoLoggingAspect {

    // add new advice for @AfterReturning on the findAccount method
    @AfterReturning(
            pointcut = "execution(* com.tilmeez.aopdemo.dao.AccountDAO.findAccounts(..))",
            returning = "result"
    )
    public void afterReturningFindAccountsAdvice(
            JoinPoint theJoinPoint, List<Account> result) {

        // print out which method we are advising on
        String method = theJoinPoint.getSignature().toShortString();
        System.out.println("\n=====>>> Executing @AfterReturning on method:  " + method);

        // print out the result of the method call
        System.out.println("\n=====>>> result is : " + result);

        // post-process the data ... modify it

        // convert the account names to Uppercase
        convertAccountNamesToUpperCase(result);

        System.out.println("\n=====>>> result is : " + result);

    }

    private void convertAccountNamesToUpperCase(List<Account> result) {

        // loop through accounts
        for (Account tempAccount : result) {

            // get uppercase version of names
            String theUpperName = tempAccount.getName().toUpperCase();

            // update the name on the account
            tempAccount.setName(theUpperName);

        }

    }

    @Before("com.tilmeez.aopdemo.aspect.AopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint theJointPoint) {
        System.out.println("\n====>>> Executing @Before advice on addAccount");

        // display the method signature
        MethodSignature methodSig = (MethodSignature) theJointPoint.getSignature();

        System.out.println("Method: " + methodSig);

        // display method arguments

        // get args
        Object[] args = theJointPoint.getArgs();

        // loop thru args
        for (Object tempArgs : args) {
            System.out.println(tempArgs);

            if (tempArgs instanceof Account) {

                // downcast and print Account specific stuff
                Account theAccount = (Account) tempArgs;

                System.out.println("account name : " + theAccount.getName());
                System.out.println("account level : " + theAccount.getLevel());

            }
        }
    }

}
