

const saveBtn = $('#saveBtn');
const cancelBtn = $('#cancelBtn');
// Profile Image
const uploadProfilePhoto = $('#uploadProfilePhoto');
const uploadBannerImage = $("#uploadBannerImage");
//Basic Information
const companyName = $('#companyName');
const companyTagline = $('#companyTagline');
const companyIndustry = $('#companyIndustry');
// Company Overview
const companyOverview = $('#companyOverview');
const companyMission = $('#companyMission');
const companyVision = $('#companyVision');
const companyLocations = $('#companyLocations');



let profileImageBase64 = '';
let bannerImageBase64 = '';
function previewAndUploadImage(event, type) {
    const file = event.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function(e) {
        const previewId = type === 'avatar' ? 'avatarPreview' : 'bannerPreview';
        const previewElem = document.getElementById(previewId);
        if(previewElem) {
            previewElem.innerHTML = `<img src="${e.target.result}" alt="${type}" style="width:100%;height:100%;object-fit:cover;" />`;
        }

        // Assign Base64 to the correct variable
        if(type === 'avatar') {
            profileImageBase64 = e.target.result;
        } else {
            bannerImageBase64 = e.target.result;
        }

        // console.log("Base64 ("+type+"):", e.target.result);
    };
    reader.readAsDataURL(file);
}

const token = localStorage.getItem("token");

$("#saveBtn").on("click", async function () {

    console.log(parseJwt(localStorage.getItem("token")));

    const token = localStorage.getItem("token");
    const email = parseJwt(token)
        console.log(token)
    // const actualToken = token.startsWith('Bearer ') ? token.substring(7) : token;


    fetch('http://localhost:8080/company/update', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token
        },
        body: JSON.stringify({
            companyName: companyName.val(),
            profileImage: profileImageBase64,
            bannerImage: bannerImageBase64,
            tagline: companyTagline.val(),
            industry: companyIndustry.val(),
              overview: companyOverview.val(),
              mission: companyMission.val(),
              vision: companyVision.val(),
              locations: companyLocations.val(),
        //       profileImage: profileImageBase64,
        //       bannerImage: bannerImageBase64,
              mali: email.sub
            })
    })
        .then(response => {
            if (response.status === 403) {
                throw new Error('403 Forbidden - Check if your role is correctly set to ROLE_COMPANY');
            }
            if (!response.ok) {
                throw new Error('HTTP error ' + response.status);
            }
            console.log(response.json())
            // return response.json();
        })


        console.log(profileImageBase64)
        console.log(bannerImageBase64)



    //
    //
    // if (!actualToken) {
    //   showResult('Please enter a JWT token first', 'error');
    //   return;
    // }
    // $.ajax({
    //   url: "http://localhost:8080/company/update",
    //   method: "POST",
    //   contentType: "application/json",
    //   headers: {
    //     "Authorization": "Bearer " + actualToken
    //   },
    //   data: JSON.stringify({
    //     companyName: companyName,
    //     tagline: companyTagline,
    //     industry: companyIndustry,
    //     followersCount: "2",
    //     overview: companyOverview,
    //     mission: companyMission,
    //     vision: companyVision,
    //     locations: selectedLocations,
    //     profileImage: profileImageBase64,
    //     bannerImage: bannerImageBase64,
    //     mali: email
    //   }),
    //   success: function(response) {
    //     alert("Profile updated successfully!");
    //     console.log(response);
    //   },
    //   error: function(xhr, status, error) {
    //     // console.error(xhr.responseText);
    //     alert("Failed to update profile.");
    //   }
    // });
    //

    // alert('Profile updated successfully!');
});


// token eke thiyena ewwa balana eka

function parseJwt (token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}
