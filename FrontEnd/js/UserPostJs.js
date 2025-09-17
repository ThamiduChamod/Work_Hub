document.addEventListener('DOMContentLoaded', () => {

    getAllPost();


});


let page = 0;
let size = 2;
let loading = false; // prevent multiple calls at once

function loadOnScroll() {
    if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight - 100 && !loading) {
        getAllPost();
    }
}

window.addEventListener("scroll", loadOnScroll); // function pass කරනවා




let jobs = [];
    function getAllPost() {
        const token = localStorage.getItem('token')

        fetch(`http://localhost:8080/user/allPost?page=${page}&size=${size}`, {
            method:'GET',
            headers:{
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            }
        }).then(res => res.json())
            .then(response => {
                const newJobs = response.data; // API එකේ page එකේ posts

                if (newJobs.length > 0) {
                    newJobs.forEach(job => {
                        if (!jobs.some(j => j.id === job.id)) { // check duplicate by ID
                            jobs.push(job);
                            renderPosts([job]); // render one by one
                        }
                    });
                    page++;
                }
                else {
                    window.removeEventListener("scroll", loadOnScroll); // ✅ remove properly

                    console.log("No more posts");
                }
            })
            .finally(() => {
                loading = false; // finish loading
            });
    }




    function formatDateTime(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString("en-GB", {
    year: "numeric",
    month: "long",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    hour12: true
});
}

    function renderPosts() {
    const container = document.getElementById("previewContainer");
    container.innerHTML = ""; // clear before rendering

    jobs.forEach(job => {
    const card = document.createElement("div");
    card.classList.add("post-card");

    card.innerHTML = `
                <div class="post-header">
// <!--                    <div class="company-logo"></div>-->
                    <div class="post-meta">
<!--                        <h3>${job.companyName}</h3>-->
                        <div class="post-date">
                            <i class="far fa-clock"></i> Posted on ${formatDateTime(job.createdAt)}
                        </div>
                    </div>
                    <div class="post-menu">
                        <i class="fas fa-ellipsis-h"></i>
                    </div>
                </div>

                <div class="post-content">
                    <div class="job-caption">
                        <span class="caption-item"><i class="fas fa-map-marker-alt"></i> ${job.address}</span>
                        <span class="caption-item"><i class="fas fa-briefcase"></i> ${job.experienceRequired}</span>
                        <span class="caption-item"><i class="fas fa-dollar-sign"></i> ${job.salaryRange}</span>
                        <span class="caption-item"><i class="fas fa-tools"></i> ${job.skills}</span>
                        <span class="caption-item"><i class="fas fa-clock"></i> ${job.jobType}</span>
                        <span class="caption-item"><i class="fas fa-users"></i> ${job.applicants} Applicants</span>
                    </div>

                    <p class="post-text">${job.jobDescription}</p>

                    <img src="${job.jobImagePath}" alt="Job Image" class="post-image">
                </div>

                <div class="post-actions">
                    <div class="post-action">
                        <i class="far fa-comments"></i> Chat
                    </div>
                    <div class="post-action">
                        <i class="fas fa-map-marker-alt"></i> location
                    </div>
                </div>
            `;

    container.appendChild(card);
});
}

    // run render

