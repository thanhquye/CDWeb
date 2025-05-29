window.onload = function () {
    const emailInput = document.getElementById("emailInput");
    const validationMessage = document.getElementById("emailValidationMessage");
    const sendMailButton = document.getElementById("btnSendMail");

    // kiểm tra input của gmail nhập vòa
    emailInput.addEventListener("input", function () {
        const email = emailInput.value;

        // Kiểm tra định dạng email trước khi gọi API
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(email)) {
            validationMessage.style.color = "red";
            validationMessage.textContent = "Gmail không hợp lệ";
            sendMailButton.disabled = true; // Ẩn nút gửi mail
            return; // Dừng ở đây, không cần gửi request
        }

        // Gửi AJAX để kiểm tra email trên server
        fetch("validate-email", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "email=" + encodeURIComponent(email)
        })
            .then(response => response.json())
            .then(data => {
                if (data.isValid) {
                    validationMessage.style.color = "#00ff00";
                    validationMessage.textContent = "Gmail hợp lệ";
                    sendMailButton.disabled = false; // Bật nút gửi mail
                } else {
                    validationMessage.style.color = "red";
                    validationMessage.textContent = "Gmail không hợp lệ";
                    sendMailButton.disabled = true; // Ẩn nút gửi mail
                }
            })
            .catch(error => {
                console.error("Error:", error);
            });
    });

    // nút gửi mail
    sendMailButton.addEventListener("click", function (e) {
        e.preventDefault();
        const email = emailInput.value;

        // Gửi yêu cầu forgot-pass như hiện tại
        fetch("forgot-pass", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "email=" + encodeURIComponent(email)
        })
            .then(response => response.json())
            .then(data => {
                alert(data.message);
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Có lỗi xảy ra, vui lòng thử lại sau!");
            });
    });
}