import React, {Component} from 'react';
import { AgGridReact } from '@ag-grid-community/react';
import { ClientSideRowModelModule } from '@ag-grid-community/all-modules';
import '@ag-grid-community/all-modules/dist/styles/ag-grid.css';
import '@ag-grid-community/all-modules/dist/styles/ag-theme-alpine.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faTrash } from '@fortawesome/free-solid-svg-icons'

export default class GridComponent extends Component {

    constructor(props) {

        super(props);

        this.eventHub = props.eventHub;

        this.eventHub.on("data", data => {
           this.setState({
               rowData: this.createLeftRowData(data)
           })
            this.render();
            console.log(data);
        });

        this.state = {
            rowIdSequence: 100,

            leftGridOptions: {
                defaultColDef: {
                    width: 100,
                    height: 50,
                    sortable: true,
                    filter: true,
                    resizable: true
                },
                rowHeight: 100,
                rowData: [],
                getRowNodeId: (data) => {
                    return data.id;
                },
                rowDragManaged: true,
                columnDefs: [
                    {field: "id", dndSource: true},
                    {field: "docType", dndSource: true},
                    {field: "companyID", dndSource: true},
                    {field: "date", dndSource: true},
                    {field: "docID", dndSource: true},
                    {field: "sign", dndSource: true},
                    {field: "amount", dndSource: true},
                ],
                animateRows: true,
                domLayout: 'autoHeight'
            },
        };

        this.render();
    }

    calcFirstTotalAmount = () => {
        let sum = 0;

        this.state.rowData?.forEach(item => {
            if(item["sign"] == "-") {
                sum -= item['amount'];
            } else if(item["sign"] == "+"){
                sum += item["amount"];
            }
        })

       return sum;
    }

    createLeftRowData(jsonArray) {
        let data = [];
        jsonArray.forEach((documentJson) => {
            data.push(this.createDataItem(documentJson));
        });
        return data;
    }

    createDataItem(documentJson) {
        documentJson.id = documentJson['docID'];
        return documentJson;
    }

    binDragOver = (event) => {
        const dragSupported = event.dataTransfer.types.indexOf('application/json') >= 0;
        if (dragSupported) {
            event.dataTransfer.dropEffect = "move";
            event.preventDefault();
        }
    };

    binDrop = (event) => {
        event.preventDefault();
        const jsonData = event.dataTransfer.getData("application/json");
        const data = JSON.parse(jsonData);

        // if data missing or data has no id, do nothing
        if (!data || data.id == null) {
            return;
        }

        const transaction = {
            remove: [data]
        };

        const rowIsInLeftGrid = !!this.state.leftGridOptions.api.getRowNode(data.id);
        if (rowIsInLeftGrid) {
            this.state.leftGridOptions.api.applyTransaction(transaction);
        }

        this.state.rowData?.forEach((row, index, arr) => {
            if(row['id'] == data.id){
                arr.splice(index, 1);
            }
        });

        this.setState({
            rowData: this.state.rowData
        });

    };

    gridDragOver = (event) => {
        const dragSupported = event.dataTransfer.types.length;

        if (dragSupported) {
            event.dataTransfer.dropEffect = 'copy';
            event.preventDefault();
        }
    };

    gridDrop = (grid, event) => {
        event.preventDefault();
        const userAgent = window.navigator.userAgent;
        const isIE = userAgent.indexOf('Trident/') >= 0;
        const jsonData = event.dataTransfer.getData(isIE ? 'text' : 'application/json');
        const data = JSON.parse(jsonData);

        // if data missing or data has no it, do nothing
        if (!data || data.id == null) {
            return;
        }

        const gridApi = this.state.leftGridOptions.api;

        // do nothing if row is already in the grid, otherwise we would have duplicates
        const rowAlreadyInGrid = !!gridApi.getRowNode(data.id);
        if (rowAlreadyInGrid) {
            console.log('not adding row to avoid duplicates in the grid');
            return;
        }

        const transaction = {
            add: [data]
        };
        gridApi.applyTransaction(transaction);
    };

    onGridReady = (params) => {
        params.api.setRowData(this.state.rowData);
        this.api = params.api;
        params.api.sizeColumnsToFit();
    };

    render() {
        return (
            <div className="outer">
                <div className="container inner-col ag-theme-alpine"
                     onDragOver={this.gridDragOver}
                     onDrop={this.gridDrop.bind(this, 'left')}>
                    <AgGridReact gridOptions={this.state.leftGridOptions}
                                 rowData={this.state.rowData}
                                 onGridReady={this.onGridReady.bind(this)}
                                 modules={[ClientSideRowModelModule]}
                                 showGrid={this.state.showGrid}
                    />
                    <div className="total">
                        <table>
                            <tr>
                                <td><p>{"Total Amount:"}
                                {this.calcFirstTotalAmount()}</p></td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div className="inner-col factory-panel">
                <span id="eBin" onDragOver={this.binDragOver}
                      onDrop={this.binDrop.bind(this)}
                      className="factory factory-bin">
                    <i>
                        <FontAwesomeIcon icon={faTrash} />
                        <span className="filename"> Trash - </span>
                    </i>
                    Drop target to destroy row
                </span>
                </div>
            </div>
        );
    }
}
