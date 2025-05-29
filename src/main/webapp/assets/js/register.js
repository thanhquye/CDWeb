const userNameInput = document.getElementById('userName');
const emailInput = document.getElementById('email');
const password = document.getElementById('password');
const retypePassword = document.getElementById('retypePassword');
const form = document.getElementById('form');
const registerBtn = document.getElementById('registerBtn');

// Hàm hiển thị thông báo lỗi
function setErrorFor(input, message) {
    const inputBox = input.parentElement;
    const small = inputBox.querySelector('small');
    inputBox.classList.remove('success');
    inputBox.classList.add('error');

    // Gán thông báo lỗi
    small.innerText = message;
}


// Hàm hiển thị thông báo hợp lệ
function setSuccessFor(input, message) {
    const formControl = input.parentElement;
    formControl.classList.remove('error');
    formControl.classList.add('success');
    let small = formControl.querySelector('small');
    if (!small) {
        small = document.createElement('small');
        formControl.appendChild(small);
    }
    small.innerText = message;
}


// Nếu muốn xóa hoàn toàn thông báo (không hiển thị text gì)
function clearMessageFor(input) {
    const formControl = input.parentElement;
    formControl.classList.remove('error', 'success');
    const small = formControl.querySelector('small');
    if (small) small.innerText = '';
}

// Kiểm tra username và email với AJAX
function checkUsernameEmail() {
    const username = userNameInput.value.trim();
    const email = emailInput.value.trim();

    if (!username && !email) return; // nếu chưa nhập gì thì thôi

    fetch('check-user-email', {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        body: `username=${encodeURIComponent(username)}&email=${encodeURIComponent(email)}`
    })
        .then(res => res.json())
        .then(data => {
            if (username) {
                if (data.usernameExists) {
                    setErrorFor(userNameInput, 'Tên người dùng đã tồn tại');
                } else {
                    console.log("Username hợp lệ");
                    setSuccessFor(userNameInput, 'Tên người dùng hợp lệ');
                }
            }
            if (email) {
                if (data.emailExists) {
                    setErrorFor(emailInput, 'Email đã được sử dụng');
                } else {
                    setSuccessFor(emailInput, 'Email hợp lệ');
                }
            }
            checkFormValidity();
        })
        .catch(err => {
            console.error('Lỗi kiểm tra username/email:', err);
        });
}

// Thêm sự kiện input để kiểm tra real-time
userNameInput.addEventListener('input', () => {
    checkUsernameEmail();
});
emailInput.addEventListener('input', () => {
    checkUsernameEmail();
});

password.addEventListener('input', checkPasswordFields);
retypePassword.addEventListener('input', checkPasswordFields);

// Kiểm tra password & retype password
function checkPasswordFields() {
    const passwordValue = password.value.trim();
    const retypePasswordValue = retypePassword.value.trim();

    // Kiểm tra độ dài của password
    if (passwordValue.length < 6) {
        setErrorFor(password, 'Mật khẩu phải từ 6 ký tự trở lên');
    } else {
        setSuccessFor(password, 'Mật khẩu hợp lệ');
    }

    // Kiểm tra xem retypePassword có rỗng không (để tránh hiển thị thông báo khi chưa nhập gì)
    if (retypePasswordValue === "") {
        clearMessageFor(retypePassword);
    } else if (passwordValue !== retypePasswordValue) {
        setErrorFor(retypePassword, 'Mật khẩu không trùng khớp');
    } else {
        setSuccessFor(retypePassword, 'Mật khẩu trùng khớp');
    }
    checkFormValidity();
}

form.addEventListener('submit', (e) => {
    e.preventDefault();
    if (!registerBtn.disabled) {
        form.submit();
    }
});

// Kiểm tra xem tất cả các trường đều hợp lệ
function checkFormValidity() {
    const isUsernameValid = userNameInput.parentElement.classList.contains('success');
    const isEmailValid = emailInput.parentElement.classList.contains('success');
    const isPasswordValid = password.parentElement.classList.contains('success');
    const isRetypeValid = retypePassword.parentElement.classList.contains('success');

    const isFormValid = isUsernameValid && isEmailValid && isPasswordValid && isRetypeValid;

    registerBtn.disabled = !isFormValid;
}