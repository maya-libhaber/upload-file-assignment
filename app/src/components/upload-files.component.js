import React, { useState, useEffect } from "react";
import {Component} from "react";
import http from "../http-common";
import EventHub from "../services/eventHub";

export default class UploadFiles extends Component {

    constructor(props) {
        super(props);

        this.eventHub = props.eventHub;

        this.state = {

            selectedFile: null
        };
    }

    onFileChange = event => {

        this.setState({ selectedFile: event.target.files[0] });

    };

    onFileUpload = () => {

        const formData = new FormData();

        formData.append(
            "file",
            this.state.selectedFile,
            this.state.selectedFile.name
        );

        console.log(this.state.selectedFile);

        http.post("rest/submit", formData)
            .then(value => {
                this.eventHub.trigger("data", value.data);
            })

    };


    fileData = () => {

        if (this.state.selectedFile) {

            return (
                <div>
                    <h2>File Details:</h2>

                    <p>File Name: {this.state.selectedFile.name}</p>

                    <p>File Type: {this.state.selectedFile.type}</p>

                </div>
            );
        } else {
            return (
                <div>
                    <br />
                    <h4>Choose before Pressing the Upload button</h4>
                </div>
            );
        }
    };

    render() {

        return (
            <div>
                <h3>
                    File Upload
                </h3>
                <div>
                    <input type="file" onChange={this.onFileChange} />
                    <button onClick={this.onFileUpload}>
                        Upload!
                    </button>
                </div>
                {this.fileData()}
            </div>
        );
    }
}
