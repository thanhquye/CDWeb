<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Forgot Pass</title>
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <link rel="stylesheet" href="assets/css/form.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
</head>

<body style="background: url('img/pxfuel.jpg') no-repeat; background-size: cover">
<div class="form">
    <div class="form-box login">
        <h2>Forgot Password</h2>
        <div class="input-box">
            <span class="icon"></span>
            <input type="email" name="email" id="emailInput" placeholder="Nhập gmail của bạn..." required>
            <label>Email</label>
            <div id="emailValidationMessage"
                 style="  margin: 10px 0px 7px;color: red; font-size: 13px;font-weight: 700"></div>
        </div>
        <button id="btnSendMail" class="btn btn-success" disabled>Send Mail</button>
        <div class="login-register">
            <p>Bạn đã có tài khoản?
                <a href="login.jsp" class="register-link">Đăng nhập</a>
            </p>
        </div>
    </div>
</div>

</body>


<script>
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
</script>

</html>
