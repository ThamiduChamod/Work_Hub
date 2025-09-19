let companies = [];

document.addEventListener('DOMContentLoaded', () => {

    getAllCompanyCards();


});
    function getAllCompanyCards() {
        const token = localStorage.getItem('token')

        fetch("http://localhost:8080/user/getAllProfile",{
            method:'GET',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(res => res.json())
            .then(response => {
                companies = response.data;
                console.log(companies.length)
                companies.forEach(c => renderCompanyCard(c));


            })
    }



// loop and render



function renderCompanyCard(company) {
    console.log("ggggggg"+company.id);


    const container = document.getElementById("companyContainer"); // card එක append වෙන්න තියෙන div

    const card = document.createElement("div");
    card.classList.add("d-flex", "flex-column", "align-items-center", "text-center", "company-card");
    card.style.cursor = "pointer";

    card.innerHTML = `
        <img src="${company.profileImagePath}" 
             alt="Company Logo" 
             class="rounded-circle mb-2" 
             width="80" height="80">
        <div class="fw-semibold" id="companyName${company.id}">
            ${company.companyName}
        </div>
        <small class="text-muted mb-2">${company.followersCount}</small>
        <button  class="btn btn-sm w-100 rounded-pill followBtn"  id="${company.id}">
            Follow
        </button>
    `;

    container.appendChild(card);


    $(`#${company.id}`).on('click', function () {
        console.log("click", company.companyName);
        const token = localStorage.getItem('token')
        const user = parseJwt(token);
        console.log(user)

        fetch("http://localhost:8080/user/addFollowers",{
            method:'POST',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },body:JSON.stringify({
                "company_id": company.id,
                "userName": user.sub
            })
        }).then(res => res.json())
            .then(response => {
                console.log(response.data)


            })
    });


}
function parseJwt(token) {
    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    let jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}

