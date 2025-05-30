window.onload = function () {
    const emailInput = document.getElementById("emailInput");
    const validationMessage = document.getElementById("emailValidationMessage");
    const sendMailButton = document.getElementById("btnSendMail");
    const countdownDisplay = document.getElementById("countdown");

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

        // Vô hiệu hóa nút ngay khi click để ngăn spam
        sendMailButton.disabled = true;

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
                swal({
                    title: data.status === "success" ? "Thành công!" : "Thất bại!",
                    text: data.message,
                    icon: data.status === "success" ? "success" : "error",
                    button: "OK",
                });
                // Khởi chạy đếm ngược 90 giây
                startCountdown(90);
            })
            .catch(error => {
                console.error("Lỗi gửi yêu cầu:", error);
                swal("Lỗi!", "Không thể gửi yêu cầu, vui lòng thử lại.", "error");
                startCountdown(90);
            });
    });

    // Hàm đếm ngược và cập nhật lại nút gửi email cũng như phần hiển thị trên JSP
    function startCountdown(seconds) {
        let remaining = seconds;
        // Cập nhật ban đầu cho nút và countdownDisplay
        sendMailButton.innerText = `Chờ ${remaining} giây`;
        countdownDisplay.textContent = `Vui lòng chờ ${remaining} giây trước khi gửi lại.`;

        const countdownInterval = setInterval(() => {
            remaining--;
            sendMailButton.innerText = `Chờ ${remaining} giây`;
            countdownDisplay.textContent = `Vui lòng chờ ${remaining} giây trước khi gửi lại.`;
            if (remaining <= 0) {
                clearInterval(countdownInterval);
                sendMailButton.innerText = "Gửi Email";
                countdownDisplay.textContent = ""; // Xóa nội dung đếm ngược khi kết thúc
                // Kích hoạt lại nút nếu email hiện tại vẫn hợp lệ
                if (/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(emailInput.value)) {
                    sendMailButton.disabled = false;
                }
            }
        }, 1000);
    }
}