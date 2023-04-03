import React, { useState } from "react";

const AirPollution = () => {
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [AirPollutionData, setAirPollutionData] = useState(null);

  const getAirPollution = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/air_quality?city=${city}&country=${country}`
      );
      const data = await response.json();
      setAirPollutionData(data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    getAirPollution();
  };

  return (
    <div>
      <h1>Air Pollution</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="country">Country:</label>
          <select
            id="country"
            name="country"
            value={country}
            onChange={(event) => setCountry(event.target.value)}
          >
            <option value="">--Please choose a country--</option>
            <option value="PT">Portugal</option>
            <option value="Canada">Canada</option>
            <option value="Mexico">Mexico</option>
          </select>
        </div>
        <div>
          <label htmlFor="city">City:</label>
          <input
            type="text"
            id="city"
            name="city"
            value={city}
            onChange={(event) => setCity(event.target.value)}
          />
        </div>
        <button type="submit">Search Air Pollution</button>
      </form>
      {AirPollutionData && (
        <div>
          <h2>{`${city}, ${country}`}</h2>
          <p>Air Quality: {AirPollutionData.airQuality}</p>
          <p>CO: {AirPollutionData.co}</p>
          <p>NO: {AirPollutionData.no}</p>
          <p>NO2: {AirPollutionData.no2}</p>
          <p>O3: {AirPollutionData.o3}</p>
          <p>SO2: {AirPollutionData.so2}</p>
          <p>PM2.5: {AirPollutionData.pm2_5}</p>
          <p>PM10: {AirPollutionData.pm10}</p>
          <p>NH3: {AirPollutionData.nh3}</p>
        </div>
      )}
    </div>
  );
};

export default AirPollution;
