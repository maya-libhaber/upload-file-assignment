import React from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import './App.css';
import UploadFiles from "./components/upload-files.component";
import EventHub from "./services/eventHub";
import GridComponent from "./components/gridComponent";
// import './styles.css';

function App() {
    let eventHub = new EventHub();
    return (
    <div className="container">
      <div className="margin">
        <h4>Upload Files Program</h4>
      </div>
        <div>
            <UploadFiles eventHub={eventHub}/>
        </div>
        <div>
            <GridComponent eventHub={eventHub}/>
        </div>
    </div>
  );
}

export default App;


