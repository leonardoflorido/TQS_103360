import { useState } from "react";
import {
  Grid,
  TextField,
  Button,
  Typography,
  Table,
  TableBody,
  TableRow,
  TableCell,
} from "@material-ui/core";
import { Autocomplete } from "@mui/material";
import countries from "iso-3166-country-list";

const countryList = countries.map((country) => country.name);

const AirPollution = () => {
  const [city, setCity] = useState("");
  const [country, setCountry] = useState("");
  const [airPollutionData, setAirPollutionData] = useState(null);

  const handleCityChange = (event) => {
    setCity(event.target.value);
  };

  const handleSearchClick = () => {
    const cityName =
      city.substring(0, 1).toUpperCase() + city.substring(1).toLowerCase();
    const countryCode = countries.find((c) => c.name === country).code;
    fetch(
      `http://localhost:8080/api/air_pollution?cityName=${cityName}&country=${countryCode}`
    )
      .then((response) => response.json())
      .then((data) => setAirPollutionData(data))
      .catch((error) => console.error(error));
  };

  return (
    <Grid container spacing={3}>
      <Grid item xs={12}>
        <Typography variant="h2" align="center">
          Air Pollution
        </Typography>
      </Grid>
      <Grid item xs={6}>
        <TextField
          label="City"
          value={city}
          onChange={handleCityChange}
          fullWidth
        />
      </Grid>
      <Grid item xs={6}>
        <Autocomplete
          value={country === "" ? null : country}
          id="country-autocomplete"
          options={countryList}
          onChange={(e, data) => setCountry(data)}
          renderInput={(params) => (
            <TextField
              {...params}
              label="Choose a country"
              variant="outlined"
            />
          )}
        />
      </Grid>
      <Grid item xs={12}>
        <Button variant="contained" color="primary" onClick={handleSearchClick}>
          Search Air Pollution
        </Button>
      </Grid>
      {airPollutionData && (
        <Grid item xs={12}>
          <Table>
            <TableBody>
              {Object.entries(airPollutionData).map(([key, value]) => (
                <TableRow key={key}>
                  <TableCell>{key}</TableCell>
                  <TableCell>{value}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Grid>
      )}
    </Grid>
  );
};

export default AirPollution;
