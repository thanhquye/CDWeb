window.onload = function () {
    // Kiểm tra xem có phần tử hiển thị đếm ngược tồn tại hay không.
    const timerEl = document.getElementById("timer");
    if (timerEl) {
        let seconds = parseInt(timerEl.innerText, 10);
        const countdownInterval = setInterval(() => {
            seconds--;
            timerEl.innerText = seconds;
            if (seconds <= 0) {
                clearInterval(countdownInterval);
                // Sau khi đếm xong, ẩn phần thông báo đếm ngược và hiện lại nút gửi lại.
                const resendTimerEl = document.getElementById("resendTimer");
                if (resendTimerEl) {
                    resendTimerEl.style.display = "none";
                }
                const resendButtonEl = document.getElementById("resendButton");
                if (resendButtonEl) {
                    resendButtonEl.style.display = "inline-block";
                }
            }
        }, 1000);
    }
};