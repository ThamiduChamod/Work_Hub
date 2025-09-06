const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');
const signup_btn =$("#signup_btn");
const nameText = $("#nameText");
const passwordText = $("#passwordText");
const confirmPasswordText = $("#confirmPasswordText");
const emailText = $("#emailText");
const otpText = $("#otpText");
const signIn_btn = $("#signIn_Btn");
const signInEmail =  $("#signInEmail");
const signInPassword =  $("#signInPassword");


signUpButton.addEventListener('click', () => {
    container.classList.add("right-panel-active");
    otpText.prop("disabled",true)
    passwordText.prop("disabled",true)
    confirmPasswordText.prop("disabled",true)
    // signup_btn.disabled = true;

});

signInButton.addEventListener('click', () => {
    container.classList.remove("right-panel-active");
    signInEmail.val().clear();
    signInPassword.val().clear();
});




const sendOtpBtn = document.getElementById("sendOtpBtn");
let timer;

sendOtpBtn.addEventListener("click", function () {
    const email = emailText.val();

    if (!email) {
        alert("Please enter your email first!");
        return;
    }
    otpText.prop("disabled",false);

    // --- මේ තැන ඔයාගේ backend එකට OTP request යවන්න ---
    console.log("OTP sent to:", email);
    $.ajax({
        url: 'http://localhost:8080/auth/otp',
        type: 'POST',
        data: JSON.stringify({
            email: emailText.val(),
            }),
        contentType: 'application/json',
        success: function (response) {
            console.log(response)
        },
        error: function (xhr, status, error){
            console.error(error)
        }
    });

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
            otpText.prop("disabled",false);

        }
    }, 1000);
});


otpText.on("input", function () {
    if (otpText.val().length===4){
        console.log("aa");
        otpText.prop("disabled",true);

        $.ajax({
           url: 'http://localhost:8080/auth/otpValidate',
            type: 'POST',
            data: JSON.stringify({
                email: emailText.val(),
                otp: otpText.val(),
                time: ""
            }),
            contentType: 'application/json',
            success: function (req) {
                nameText.prop("disabled",true);
                emailText.prop("disabled",true);
                otpText.css("color", "green");
                otpText.prop("disabled",true);

                passwordText.prop("disabled",false);
                confirmPasswordText.prop("disabled",false);


                console.log(req)
            },
            error: function (error) {
                otpText.css("color", "read");
                console.error(error)
            }
        });


    }
});

if (confirmPasswordText.val()){
    signup_btn.disabled = false;
}


signup_btn.on("click", function () {

    if (nameText.val() && emailText.val() && otpText.val() && passwordText.val() && confirmPasswordText.val()){
        if (passwordText.val() === confirmPasswordText.val()){
            $.ajax({
                url: 'http://localhost:8080/auth/register',
                type: 'POST',
                data: JSON.stringify({
                    username: emailText.val(),
                    password: passwordText.val(),
                    role: "USER"
                }),
                contentType:'application/json',
                success: function (res) {

                    nameText.val("");
                    emailText.val("");
                    otpText.val("");
                    passwordText.val("");
                    confirmPasswordText.val("");
                    console.log(req);
                    alert("sign up successfully");

                    localStorage.setItem("token", res.data);
                    window.location.assign("UserHomePage.html");
                },
                error: function (error) {
                    console.error(error);
                }
            });
        }
    }else {
        alert("Please fill the form first!");
    }
});

signIn_btn.on("click", function () {

    console.log("clicked");
    if (!signInEmail.val() || !signInPassword.val()) {
        alert("Please enter your email & password first!");
        return;
    }
    console.log(signInEmail.val());
    console.log(signInPassword.val());


    $.ajax({
        url: 'http://localhost:8080/auth/login',
        type: 'POST',
        data: JSON.stringify({
            username: signInEmail.val(),
            password: signInPassword.val(),
        }),
        contentType:'application/json',
        success: function (res) {

            signInEmail.val("");
            signInPassword.val("");

            console.log(req);
            alert("sign up successfully");

            localStorage.setItem("token", res.data);
            window.location.assign("UserHomePage.html");
        },
        error: function (error) {
            console.error(error);
        }
    });


});
