import React, {useState} from "react";
import {Redirect, Route, Switch, withRouter} from "react-router";
import Home from "./Home";
import CustomerHeader from "../cutomerPage/CustomerHeader";
import OwnerHeader from "../ownerPage/OwnerHeader";



const MainPage =  (props) => {
    const [selectedZone, setSelectedZone] = useState(null)

    return (
        <div>
          <div>
              <Switch>
                  {console.log("inside switch")}
                  <Route exact path='/' render={() => (<Home isOwner={props.isOwner} setSelectedZone={setSelectedZone}/>)}></Route>
                  <Route path='/customerPage' render={() => (<CustomerHeader zoneName={selectedZone}/>)}></Route>
                  <Route path='/ownerPage' render={() => (<OwnerHeader zoneName={selectedZone}/>)}></Route>
              </Switch>
              <Redirect to='/' />
          </div>
        </div>
    );

}

export default MainPage;