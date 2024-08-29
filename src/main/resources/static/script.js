
document.getElementById('phone-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const phoneNumber = document.getElementById('phone').value;

    if (phoneNumber.length < 10) {
        document.getElementById('result').innerHTML = `<p style="color: red;">The phone number must contain at least 10 digits.</p>`;
        return;
    }

    fetch('/api/resolve', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ phoneNumber: phoneNumber })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('result').innerHTML = `<h3>Страны: ${data.countries}</h3>`;
        })
        .catch(error => {
            document.getElementById('result').innerHTML = `<p style="color: red;">Error: ${error.message}</p>`;
        });
});

