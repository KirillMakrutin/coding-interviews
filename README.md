# QA reports and issues for the project
Fix the issue that is happening when placing an order with the 'Pay Later' option.

## Application Logic
Every time user open http://localhost:8080/bookstore/order, their balances are reset to initial values that QA can test different payment scenarios.

## QA Observations
- When I open http://localhost:8080/bookstore/order and place an order with the 'Pay Later', then everything works.
- But I'm get the error message `Error: Placing order failed. Please try again.`, when I open http://localhost:8080/bookstore/order and
  - first place an order with Visa card
  - then place another order with 'Pay Later' 
- When I open http://localhost:8080/bookstore/order and first place an order with MasterCard and then with 'Pay Later', 
it also works fine.

## Log Output
```
c.b.paymentflow.service.OrderService: Error while creating order Order(orderId=17, userId=1, bookId=2, paymentProfileId=null, deliveryAddressId=work, paid=false)
java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because the return value of "com.bookstore.paymentflow.model.OrderRequest.getPayLater()" is null
	at com.bookstore.paymentflow.service.OrderService.createOrder(OrderService.java:72) ~[classes/:na]
	at com.bookstore.paymentflow.controller.BookStoreController.createOrder(BookStoreController.java:53) ~[classes/:na]
```

## Q: What coding practices should be followed to avoid such issues in the future?
