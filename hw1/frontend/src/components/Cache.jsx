import React, { useState, useEffect } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Grid,
  Typography,
} from "@material-ui/core";

const Cache = () => {
  const [cacheStats, setCacheStats] = useState({});
  const [cacheData, setCacheData] = useState({});

  useEffect(() => {
    // Fetch cache stats and cache data from API
    const fetchData = async () => {
      const statsResponse = await fetch(
        "http://localhost:8080/api/cache/stats"
      );
      const statsJson = await statsResponse.json();
      setCacheStats(statsJson);

      const dataResponse = await fetch("http://localhost:8080/api/cache/data");
      const dataJson = await dataResponse.json();
      setCacheData(dataJson);
    };

    fetchData();

    // Fetch data every 2 seconds
    const interval = setInterval(fetchData, 2000);

    // Cleanup function to clear interval when component unmounts
    return () => clearInterval(interval);
  }, []);

  return (
    <div>
      <Grid item xs={12}>
        <Typography variant="h2" align="center">
          Cache
        </Typography>
      </Grid>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Stat</TableCell>
              <TableCell>Value</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {Object.keys(cacheStats).length > 0 &&
              Object.entries(cacheStats).map(([key, value]) => (
                <TableRow key={key}>
                  <TableCell>{key}</TableCell>
                  <TableCell>{value}</TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
      </TableContainer>

      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>City Name</TableCell>
              <TableCell>Country</TableCell>
              <TableCell>Latitude</TableCell>
              <TableCell>Longitude</TableCell>
              <TableCell>Air Quality</TableCell>
              <TableCell>CO</TableCell>
              <TableCell>NO</TableCell>
              <TableCell>NO2</TableCell>
              <TableCell>O3</TableCell>
              <TableCell>SO2</TableCell>
              <TableCell>PM2.5</TableCell>
              <TableCell>PM10</TableCell>
              <TableCell>NH3</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {Object.keys(cacheData).map((key) => (
              <TableRow key={key}>
                <TableCell>{cacheData[key].cityName}</TableCell>
                <TableCell>{cacheData[key].country}</TableCell>
                <TableCell>{cacheData[key].lat}</TableCell>
                <TableCell>{cacheData[key].lon}</TableCell>
                <TableCell>{cacheData[key].airQuality}</TableCell>
                <TableCell>{cacheData[key].co}</TableCell>
                <TableCell>{cacheData[key].no}</TableCell>
                <TableCell>{cacheData[key].no2}</TableCell>
                <TableCell>{cacheData[key].o3}</TableCell>
                <TableCell>{cacheData[key].so2}</TableCell>
                <TableCell>{cacheData[key]["pm2_5"]}</TableCell>
                <TableCell>{cacheData[key].pm10}</TableCell>
                <TableCell>{cacheData[key].nh3}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default Cache;
