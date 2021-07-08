import axios from "axios";

export default axios.create({
    baseURL: process.env.API_URL,
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Headers": "X-Requested-With, Origin, content-type, Authorization, Accept"
    },
    crossDomain: true

})
