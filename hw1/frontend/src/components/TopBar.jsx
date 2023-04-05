import { AppBar, Toolbar, Typography, Button } from "@material-ui/core";
import { Link } from "react-router-dom";

const TopBar = () => {
  return (
    <AppBar position="static">
      <Toolbar>
        <Button component={Link} to="/" color="inherit">
          Air Pollution
        </Button>
        <Button component={Link} to="/cache" color="inherit">
          Cache
        </Button>
      </Toolbar>
    </AppBar>
  );
};

export default TopBar;
