# AOP: @AfterReturning Advice Type

**AOP: @AfterReturning Overview**

_@AfterReturning Advice - Interaction_

<img src="https://user-images.githubusercontent.com/80107049/189656320-733ea38e-07b5-407e-a871-576dc4803a62.png" width=500 />



**@AfterReturning Advice - Use Cases**
+ **Most common**
  + logging, security, transactions

+ **Audit logging**
  + who, what, when, where

+ **Post-processing Data**
  + Post process the data before returning to caller
  + Formate the data or enrich the data (really cool but be careful)

**Example**
+ Create an advice that will run after a method call (success execution)

<img src="https://user-images.githubusercontent.com/80107049/189656408-5af296c1-470f-4ede-ba81-acbf1a1cac5c.png" width=500 />


+ This advice will run after the method call (success execution)
```JAVA
@AfterReturning("execution(* com.tilmeez.aopdemo.dao.AccountDAO.findAccounts(..))")
public void afterReturningFindAccountsAdvice() {
  
  System.out.println("Execting @AfterReturning advice");
  
}
```

**Access the Return Value**

+ Need to access the return value of method called

```JAVA
@AfterReturning(
  pointcut="execution(* com.tilmeez.aopdemo.dao.AccountDAO.findAccount(..))",
  returning="result")
public void afterReturningFindAccountAdvice(
  JoinPoint theJoinPoint, List<Acount> result) {
  
  // print out the results of the method call
  System.out.println("\n=====>>> result is: " + result);
  
}
```

+ `returning="result"` Parameter name for return value `List<Acount> result`
  + Can use Any parameter name ... just stay consistent



**Development Process - @AfterReturning**

1. Prep Work: Add constructor to Account class
2. Add new method: findAccounts() in AccountDAO
3. Update main app to call new method: findAccount()
4. Add @AfterReturning advice

***

**Post_Process / Modify Data**

<img src="https://user-images.githubusercontent.com/80107049/189671730-001affe9-dca3-45de-9e81-1b7f1010c300.png" width=500 />

_Modify the Return Value_

```JAVA
@AfterReturning(
  pointcut="execution(* com.tilmeez.aopdemo.dao.AccountDAO.findAccount(..))",
  returning="result")
public void afterReturningFindAccountAdvice(
  JoinPoint theJointPoint, List<Account> result) {
  
  // moddify "result" list: add, remove, update, etc ...
  if (result.isEmpty()) {
    
  Account tempAccount = result.get(0);
  
  tempAccount.setName("Daffy Duck");
  }
}
```

_Calling program_
```JAVA
// all method to find the accounts
List<Account> theAccounts = theAccountDAO.findAccounts();
```