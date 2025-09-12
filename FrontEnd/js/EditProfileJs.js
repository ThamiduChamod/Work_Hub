// const saveBtn = $('#saveBtn');
// const cancelBtn = $('#cancelBtn');
// // Profile Image
// const uploadProfilePhoto = $('#uploadProfilePhoto');
// const uploadBannerImage = $("#uploadBannerImage");
// //Basic Information
// const companyName = $('#companyName');
// const companyTagline = $('#companyTagline');
// const companyIndustry = $('#companyIndustry');
// // Company Overview
// const companyOverview = $('#companyOverview');
// const companyMission = $('#companyMission');
// const companyVision = $('#companyVision');
// const companyLocations = $('#companyLocations');
//
//
//
// let profileImageBase64 = '';
// let bannerImageBase64 = '';
//
// function previewAndUploadImage(event, type) {
//     const file = event.target.files[0];
//     if (!file) return;
//
//     const reader = new FileReader();
//     reader.onload = function(e) {
//         const previewId = type === 'avatar' ? 'avatarPreview' : 'bannerPreview';
//         const previewElem = document.getElementById(previewId);
//         if(previewElem) {
//             previewElem.innerHTML = `<img src="${e.target.result}" alt="${type}" style="width:100%;height:100%;object-fit:cover;" />`;
//         }
//     };
//     reader.readAsDataURL(file);
// }
// const token = localStorage.getItem("token");
//
// $("#saveBtn").on("click", async function () {
//
//     // const email = localStorage.getItem("email");
//     console.log(token)
//     const email = 'thamiduchamode100@gmail.com'
//     // try {
//     //     const response = await fetch("http://localhost:8080/company/update", {
//     //         method: "POST",
//     //         headers: {
//     //             "Content-Type": "application/json",
//     //             "Authorization": "Bearer " + token // token must exist
//     //         }
//     //         ,
//     //         body: JSON.stringify({
//     //             companyName: companyName,
//     //             tagline: companyTagline,
//     //             industry: companyIndustry,
//     //             followersCount: "2",
//     //             overview: companyOverview,
//     //             mission: companyMission,
//     //             vision: companyVision,
//     //             locations: selectedLocations,
//     //             profileImage: profileImageBase64,
//     //             bannerImage: bannerImageBase64,
//     //             mali: email
//     //         })
//     //
//     //     });
//     //
//     // } catch (err) {
//     //     console.error(err);
//     //     alert('Failed to update profile.');
//     // }
//     // const token = localStorage.getItem("token");
//     // const email = 'thamiduchamode100@gmail.com';
//
//     $.ajax({
//         url: "http://localhost:8080/company/update",
//         method: "POST",
//         contentType: "application/json",
//         headers: {
//             "Authorization": "Bearer " + token
//         },
//         data: JSON.stringify({
//             companyName: companyName,
//             tagline: companyTagline,
//             industry: companyIndustry,
//             followersCount: "2",
//             overview: companyOverview,
//             mission: companyMission,
//             vision: companyVision,
//             locations: selectedLocations,
//             profileImage: profileImageBase64,
//             bannerImage: bannerImageBase64,
//             mali: email
//         }),
//         success: function(response) {
//             alert("Profile updated successfully!");
//             console.log(response);
//         },
//         error: function(xhr, status, error) {
//             // console.error(xhr.responseText);
//             alert("Failed to update profile.");
//         }
//     });
//
//
//     // alert('Profile updated successfully!');
// });
//
//
//
//
//
//
//
// // Buttons
// const saveBtn = $('#saveBtn');
// const cancelBtn = $('#cancelBtn');
//
// // Image inputs
// const uploadProfilePhoto = $('#uploadProfilePhoto');
// const uploadBannerImage = $('#uploadBannerImage');
//
// // Basic Information inputs
// const companyName = $('#companyName');
// const companyTagline = $('#companyTagline');
// const companyIndustry = $('#companyIndustry');
//
// // Company Overview inputs
// const companyOverview = $('#companyOverview');
// const companyMission = $('#companyMission');
// const companyVision = $('#companyVision');
// const companyLocations = $('#companyLocations');
//
// // Base64 storage
// let profileImageBase64 = '';
// let bannerImageBase64 = '';
// let selectedLocations = []; // make sure this array is populated properly
//
// // Image preview + Base64 conversion
// function previewAndUploadImage(event, type) {
//     const file = event.target.files[0];
//     if (!file) return;
//
//     const reader = new FileReader();
//     reader.onload = function(e) {
//         const previewId = type === 'avatar' ? 'avatarPreview' : 'bannerPreview';
//         const previewElem = document.getElementById(previewId);
//         if(previewElem){
//             previewElem.innerHTML = `<img src="${e.target.result}" alt="${type}" style="width:100%;height:100%;object-fit:cover;" />`;
//         }
//
//         // Save Base64
//         if(type === 'avatar') profileImageBase64 = e.target.result.split(',')[1];
//         else bannerImageBase64 = e.target.result.split(',')[1];
//     };
//     reader.readAsDataURL(file);
// }
//
// // Attach image change events
// uploadProfilePhoto.on('change', (e) => previewAndUploadImage(e, 'avatar'));
// uploadBannerImage.on('change', (e) => previewAndUploadImage(e, 'banner'));
//
// // Get token
// const token = localStorage.getItem("token");
//
// // Save button click
// saveBtn.on('click', function() {
//     const email = 'thamiduchamode100@gmail.com'; // or from token
//
//     // Collect values from inputs
//     const dataToSend = {
//         companyName: companyName.val(),
//         tagline: companyTagline.val(),
//         industry: companyIndustry.val(),
//         followersCount: "2",
//         overview: companyOverview.val(),
//         mission: companyMission.val(),
//         vision: companyVision.val(),
//         locations: selectedLocations,
//         profileImage: profileImageBase64,
//         bannerImage: bannerImageBase64,
//         mali: email
//     };
//
//     // AJAX POST
//     $.ajax({
//         url: "http://localhost:8080/company/update",
//         method: "POST",
//         contentType: "application/json",
//         headers: {
//             "Authorization": "Bearer " + token
//         },
//         data: JSON.stringify(dataToSend),
//         success: function(response){
//             alert("Profile updated successfully!");
//             console.log(response);
//         },
//         error: function(xhr, status, error){
//             console.error(xhr.responseText);
//             alert("Failed to update profile. Check console for details.");
//         }
//     });
// });
