/*fetch("/stats/total")
    .then(res => res.json())
    .then(data => {
        document.getElementById("totalCount").innerText = data;
    });

fetch("/stats/api")
    .then(res => res.json())
    .then(data => {
        const table = document.getElementById("apiTable");
        data.forEach(row => {
            table.innerHTML += `
                <tr>
                    <td>${row.endpoint}</td>
                    <td>${row.count}</td>
                </tr>
            `;
        });
    });

fetch("/stats/user")
    .then(res => res.json())
    .then(data => {
        const table = document.getElementById("userTable");
        data.forEach(row => {
            table.innerHTML += `
                <tr>
                    <td>${row.email}</td>
                    <td>${row.count}</td>
                </tr>
            `;
        });
    });
*/