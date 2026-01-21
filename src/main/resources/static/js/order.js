(function () {
  console.log("Order JS loaded");

  class Client {
    constructor(url) {
      this.url = url;
    }

    placeOrder = (orderDetails) => {
      console.log("Placing order with details:", orderDetails);
      return fetch(this.url + "/bookstore/order", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          orderedBookId: orderDetails.orderedBook,
          payLate: orderDetails.payLater,
          useBonus: orderDetails.useBonus,
          paymentProfileId: orderDetails.paymentProfile,
          deliveryAddressId: orderDetails.deliveryAddress
        })
      })
      .then(resp => {
        if (resp.ok) {
          return resp.json();
        } else if (resp.status === 400) {
          return resp.json()
          .then(err => {
            throw new Error(err.message);
          });
        } else {
          throw new Error("Something went wrong while placing the order");
        }
      })
      .catch(err => {
        console.error("Error in placeOrder:", err);
        return {
          error: true,
          message: "Placing order failed. Please try again."
        };
      });
    }
  }

  class PaymentOrderComponent {
    constructor(client) {
      this.client = client;
      this.state = {
        orderedBook: null,
        deliveryAddress: null,
        paymentProfile: null,
        payLater: false,
        useBonus: false
      }

      this.controls = {
        orderedBookSelect: document.getElementById("orderedBook"),
        deliveryAddressInput: document.getElementById("deliveryAddress"),
        paymentProfileSelect: document.getElementById("paymentProfile"),
        payLaterCheckbox: document.getElementById("payLater"),
        useBonusCheckbox: document.getElementById("useBonus"),
        useBonusLabel: document.getElementById("useBonusLabel"),
        orderButton: document.getElementById("orderButton")
      }

      this.init();
      this.render();
    }

    init = () => {
      this.state.orderedBook = this.controls.orderedBookSelect.value;
      this.state.deliveryAddress = this.controls.deliveryAddressInput.value;
      this.state.paymentProfile = this.controls.paymentProfileSelect.value;
      this.state.payLater = this.controls.payLaterCheckbox.checked;
      this.state.useBonus = this.controls.useBonusCheckbox.checked;

      console.log(
          "PaymentOrderComponent initialized with client URL and initial state:",
          this.client.url, this.state);

      this.controls.orderedBookSelect.addEventListener("change",
          this.handleOrderBookChange);
      this.controls.deliveryAddressInput.addEventListener("input",
          this.handleDeliveryAddressChange);
      this.controls.paymentProfileSelect.addEventListener("change",
          this.handlePaymentProfileChange);
      this.controls.payLaterCheckbox.addEventListener("change",
          this.handlePayLaterChange);
      this.controls.useBonusCheckbox.addEventListener("change",
          this.handleUserBonusChange);
      this.controls.orderButton.addEventListener("click",
          this.handleOrderButtonClick);
    }

    handlePayLaterChange = (event) => {
      const payLater = event.target.checked;

      if (payLater) {
        this.state.paymentProfile = null;
      } else if (!this.state.paymentProfile) {
        this.state.paymentProfile = this.controls.paymentProfileSelect.value;
      }

      this.state.payLater = payLater;
      this.render();
    }

    handleUserBonusChange = (event) => {
      let useBonus = event.target.checked;
      this.state.useBonus = useBonus;

      if (useBonus) {
        this.state.payLater = false;
        this.state.paymentProfile = null;
        this.controls.payLaterCheckbox.checked = false;
      } else if (!this.state.paymentProfile) {
        this.state.paymentProfile = this.controls.paymentProfileSelect.value;
      }

      this.render();
    }

    handlePaymentProfileChange = (event) => {
      this.state.paymentProfile = event.target.value;
      this.render();
    }

    handleDeliveryAddressChange = (event) => {
      this.state.deliveryAddress = event.target.value;
      this.render();
    }

    handleOrderBookChange = (event) => {
      this.state.orderedBook = event.target.value;
      this.render();
    }

    formatOrderDetails = (details) => {
      return `
        <p>You have successfully placed an order!</p>
        <p>Ordered Book: ${details.book.name} by ${details.book.author}</p>
        <p>Delivery Address: ${details.deliveryAddress.value}</p>
      `;
    }

    handleOrderButtonClick = () => {
      this.client.placeOrder(this.state)
      .then(data => {
        if (data.error) {
          new OrderDialogComponent("Error: " + data.message).openDialog();
        } else {
          this.controls.useBonusLabel.textContent = `Use Bonus Points (You have $${data.bonusBalance} points)`;
          new OrderDialogComponent(this.formatOrderDetails(data)).openDialog();
        }
      })
      .catch(error => {
        console.error("Error placing order:", error);
        new OrderDialogComponent("Failed to place order.").openDialog();
      });
    }

    render = () => {
      console.log("Rendering PaymentOrderComponent with state:", this.state);
      this.controls.payLaterCheckbox.disabled = this.state.useBonus;
      this.controls.paymentProfileSelect.disabled = this.state.payLater
          || this.state.useBonus;
    }
  }

  class OrderDialogComponent {
    constructor(dialogContent) {
      this.dialog = new bootstrap.Modal(document.getElementById("orderDialog"));
      document.getElementById("orderDialog_body").innerHTML = dialogContent;
    }

    openDialog = () => {
      this.dialog.show();
    }
  }

  const client = new Client("http://localhost:8080");
  const paymentOrderComponent = new PaymentOrderComponent(client);
  paymentOrderComponent.render();
})()
