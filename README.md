QA reports and issues for the project
========================================================
Fix the issue that is happening when placing an order with the 'Pay Later' option after a Visa card order.

Steps to Reproduce
------------------
1. Follow http://localhost:8080/bookstore/order
2. Place an order for any book with Visa card
3. Observe the order confirmation page
4. Place another order for any book with 'Pay Later' option
5. Observer the error message `Error: Placing order failed. Please try again.`

Note
------------------
1. If QA follows the same steps but choose a MasterCard method before the 'Pay Later', the order is processed successfully without any errors.
2. If QA follows http://localhost:8080/bookstore/order for the 1st time and directly chooses the 'Pay Later' option, the order is processed successfully without any errors.

Log Output
------------------
```
c.b.paymentflow.service.OrderService: Error while creating order Order(orderId=17, userId=1, bookId=2, paymentProfileId=null, deliveryAddressId=work, paid=false)
java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because the return value of "com.bookstore.paymentflow.model.OrderRequest.getPayLater()" is null
	at com.bookstore.paymentflow.service.OrderService.createOrder(OrderService.java:72) ~[classes/:na]
	at com.bookstore.paymentflow.controller.BookStoreController.createOrder(BookStoreController.java:53) ~[classes/:na]
```
