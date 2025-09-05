const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const signup_btn =$("#signup_btn");
const nameText = $("#nameText");
const passwordText = $("#passwordText");
const confirmPasswordText = $("#confirmPasswordText");
const emailText = $("#emailText");
const otpText = $("#otpText");


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
    otpText.prop("disabled",true)
    passwordText.prop("disabled",true)
    confirmPasswordText.prop("disabled",true)

});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
});




const sendOtpBtn = document.getElementById("sendOtpBtn");
let timer;

sendOtpBtn.addEventListener("click", function () {
    const email = emailText.value;

    if (!email) {
        alert("Please enter your email first!");
        return;
    }
    otpText.prop("disabled",false);

    // --- මේ තැන ඔයාගේ backend එකට OTP request යවන්න ---
    console.log("OTP sent to:", email);

    // Disable button and start countdown
    let timeLeft = 60;
    sendOtpBtn.disabled = true;
    sendOtpBtn.textContent = `${timeLeft}s`;

    timer = setInterval(() => {
        timeLeft--;
        sendOtpBtn.textContent = `${timeLeft}s`;

        if (timeLeft <= 0) {
            clearInterval(timer);
            sendOtpBtn.disabled = false;
            sendOtpBtn.textContent = "Send OTP";
        }
    }, 1000);
});


otpText.on("input", function () {
    if (otpText.val().length===4){
        console.log("aa");
        otpText.prop("disabled",true);


        passwordText.prop("disabled",false);
        confirmPasswordText.prop("disabled",false);
    }
});



signup_btn.on("click", function () {
    if (nameText.value && emailText.value && otpText.value && passwordText.value && confirmPasswordText.value){
        if (passwordText.val() === confirmPasswordText.val()){

        }
    }else {
        alert("Please fill the form first!");
    }

});
