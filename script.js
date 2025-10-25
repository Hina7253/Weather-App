const apiKey = "e2e1a616fcba7e80bf9913862f88a52e";

// कई शहरों की लिस्ट
const cities = ["Delhi", "Mumbai", "Kolkata", "Chennai", "Bangalore"];

function getWeather() {
    document.getElementById("result").innerHTML = ""; // पुराना डेटा साफ करें

    cities.forEach(city => {
        const url = `https://api.openweathermap.org/data/2.5/weather?q=${city}&appid=${apiKey}&units=metric&lang=hi`;

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`${city} का डेटा नहीं मिला`);
                }
                return response.json();
            })
            .then(data => {
                document.getElementById("result").innerHTML += `
                    <div style="border:1px solid #ccc; padding:10px; margin:10px; border-radius:8px; background:white;">
                        <h3>${data.name}, ${data.sys.country}</h3>
                        <p>🌡 तापमान: ${data.main.temp}°C</p>
                        <p>💧 नमी: ${data.main.humidity}%</p>
                        <p>🌬 हवा: ${data.wind.speed} m/s</p>
                        <p>☁ मौसम: ${data.weather[0].description}</p>
                    </div>
                `;
            })
            .catch(error => {
                document.getElementById("result").innerHTML += `<p style="color:red;">${error.message}</p>`;
            });
    });
}
