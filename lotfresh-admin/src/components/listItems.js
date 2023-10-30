import * as React from 'react';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import ListSubheader from '@mui/material/ListSubheader';
import DashboardIcon from '@mui/icons-material/Dashboard';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import PeopleIcon from '@mui/icons-material/People';
import AssignmentIcon from '@mui/icons-material/Assignment';
import { Link } from 'react-router-dom';


export const mainListItems = (
  <React.Fragment>
     <ListSubheader component="div" inset>
      Account Management
    </ListSubheader>
    <ListItemButton component={Link} to="/Users">
      <ListItemIcon>
        <AssignmentIcon />
      </ListItemIcon>
      <ListItemText primary="Users" />
    </ListItemButton>
    <ListItemButton component={Link} to="/Admins">
      <ListItemIcon>
        <PeopleIcon />
      </ListItemIcon>
      <ListItemText primary="Admins" />
    </ListItemButton>
  </React.Fragment>
);

export const secondaryListItems = (
  <React.Fragment>
    <ListSubheader component="div" inset>
      Products Management
    </ListSubheader>
    <ListItemButton component={Link} to="/Storages">
      <ListItemIcon>
        <DashboardIcon />
      </ListItemIcon>
      <ListItemText primary="Storage Products" />
    </ListItemButton>
    <ListItemButton component={Link} to="/Products">
      <ListItemIcon>
        <ShoppingCartIcon  />
      </ListItemIcon>
      <ListItemText primary="Products" />
    </ListItemButton>

  </React.Fragment>
);
