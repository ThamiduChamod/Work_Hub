 const post = $('#post');

let base64String = '';

 function previewAndUploadImage(event) {
     const file = event.target.files[0];
     if (!file) return;

     const reader = new FileReader();

     reader.onload = function(e) {
         // Base64 String (data:image/png;base64,....)
          base64String = e.target.result;

         console.log("Base64 Image:", base64String);


         // Hidden input එකකට set කරන්න (backendට යවන්න easy)
         document.getElementById("hiddenBase64").value = base64String;
     };

     reader.readAsDataURL(file);
 }

 function parseJwt (token) {
     const base64Url = token.split('.')[1];
     const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
     const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
         return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
     }).join(''));
     return JSON.parse(jsonPayload);
 };
  const token = localStorage.getItem("token");
 const user = (parseJwt(token));

//
//  const playLoad = {
//     "jobTitle": $('#jobTitle').val(),
//     "address": $('#address').val(),
//     "location":$('#jobLocations').val() ,
//     "experienceRequired":  $('#experience').val(),
//     "salaryRange":  $('#salary').val(),
//     "jobType":  $('#jobType').val(),
//     "workMode":  $('#workMode').val(),
//     "skills":  $('#skillInput').val(),
//     "jobDescription":  $('#jobContent').val(),
//     "jobImagePath":  base64String,
//     "createdAt": new Date(),
//     "userName":  user.sub
// }

post.on('click', function () {
    console.log($('#jobTitle').val())
    console.log($('#address').val())
    console.log($('#jobLocations').val())
    console.log($('#experience').val())
    console.log( $('#salary').val())
    console.log($('#jobType').val())
    console.log($('#workMode').val())
    console.log( $('#jobContent').val())
    console.log(base64String)

    console.log(user.sub)

    fetch( 'http://localhost:8080/company/postJob', {
        method: 'POST',
        headers:{
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({
            "jobTitle": $('#jobTitle').val(),
            "address": $('#address').val(),
            "location":$('#jobLocations').val() ,
            "experienceRequired":  $('#experience').val(),
            "salaryRange":  $('#salary').val(),
            "jobType":  $('#jobType').val(),
            "workMode":  $('#workMode').val(),
            "skills":  $('#skillInput').val(),
            "jobDescription":  $('#jobContent').val(),
            "jobImagePath":  base64String,
            "createdAt": new Date(),
            "userName":  user.sub
        })
    }).then(response =>{
        if (response.status === 403) {
            throw new Error('403 Forbidden - Check if your role is correctly set to ROLE_COMPANY');
        }
        if (!response.ok) {
            throw new Error('HTTP error ' + response.status);
        }
        console.log(response.json())
        // return response.json();
    });



})



