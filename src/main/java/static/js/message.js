let host_mess = "http://localhost:8888/rest/message";

app.controller("message-ctrl", function ($scope, $http) {
  $scope.formatDateToDDMMYYYY = function (dateString) {
    const date = new Date(dateString); // Chuyển chuỗi thời gian thành đối tượng Date
    const options = { day: "numeric", month: "numeric", year: "numeric" };
    return date.toLocaleDateString("vi-VN", options); // Chuyển đổi sang "dd/MM/yyyy"
  };

  $scope.formatDateToCustomString = function (dateString) {
    const date = new Date(dateString);
    const months = [
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Sep",
      "Oct",
      "Nov",
      "Dec",
    ];

    const hours = date.getHours();
    const minutes = date.getMinutes();
    const month = months[date.getMonth()];
    const day = date.getDate();

    // Định dạng giờ theo AM/PM
    const period = hours >= 12 ? "PM" : "AM";
    const formattedHours = hours % 12 || 12;

    // Chuỗi kết quả
    const formattedString = `${formattedHours}:${
      minutes < 10 ? "0" : ""
    }${minutes} ${period} | ${month} ${day}`;

    return formattedString;
  };

  $scope.formatDateTimeToDDMMYYYYHHMM = function (dateString) {
    const date = new Date(dateString);
    const options = {
      day: "numeric",
      month: "numeric",
      year: "numeric",
      hour: "numeric",
      minute: "numeric",
    };
    return date.toLocaleDateString("vi-VN", options);
  };

  //Quản lí tin nhắn
  var $message = ($scope.message = {
    list: [],
    showMessageChecked: [],
    groupMessageChat: {},
    selectedIdReceiver: "",

    // load tin nhắn
    loadMessage() {
      var url = `${host_mess}/list`;
      $http.get(url).then((response) => {
        this.list = response.data;
        this.showGroupChat();
        console.log(this.list);
        console.log(this.groupMessageChat);
      });
    },

    //hiện thị đoạn chat mình chọn
    showMessageChatOne(idUser) {
      let arrTo = this.groupMessageChat[idUser];
	  let arrFrom = this.list.filter((item) => item.senderID !== item.userID && item.senderID === arrTo[0].receiverID);
      this.showMessageChecked = [...arrTo, ...arrFrom];
	   // Sắp xếp các sản phẩm trong từng cụm theo CreatedAt tăng dần
	   this.showMessageChecked.sort((a, b) => {
        const dateA = new Date(a.createdAt);
        const dateB = new Date(b.createdAt);
        return dateA - dateB;
      });
      this.selectedIdReceiver = idUser;
    },

    //check xem tin nhắn đó là của bên nào
    checkPersonChat(idUser) {},

    //hiện thị đối tượng chat sidepart
    showGroupChat() {
      this.groupMessageChat = {};
      this.list.forEach((item) => {
		if(item.senderID === item.userID) {
			if (!this.groupMessageChat[item.receiverID]) {
				this.groupMessageChat[item.receiverID] = [];
			  }
			  this.groupMessageChat[item.receiverID].push(item);
		}
          
      });

      // Chuyển đối tượng thành mảng các cặp key-value
      const objArray = Object.entries(this.groupMessageChat);
      // Sắp xếp mảng objArray dựa trên trường createdAt giảm dần
      objArray.sort((a, b) => {
        const dateA = new Date(a[1][0].createdAt).getTime();
        const dateB = new Date(b[1][0].createdAt).getTime();
        return dateB - dateA;
      });
      // Chuyển lại thành đối tượng từ mảng objArray đã sắp xếp
      this.groupMessageChat = Object.fromEntries(objArray);
    },
  });

  $message.loadMessage();
});
