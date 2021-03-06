/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



function create_table_from_json_array(json, table_id)
{
    var table = document.getElementById(table_id);
    table.innerHTML=""
    var total = json.length;
    var cell = new Array();
    //add header
    var header = table.createTHead();
    var row = header.insertRow(0);
    var count = 0
    for (var js in json[0]) {
        var cell = row.insertCell(count++);
        cell.innerHTML = js
    }
    for (var i = 0; i < total; i++) {
        var row = table.insertRow(i + 1);
        for (var j = 0; j < Object.keys(json[0]).length; j++) {
            cell[j] = row.insertCell(j);

        }
        var index = 0

        for (var js in json[i]) {
            cell[index++].innerHTML = json[i][js]
        }

    }
}

function close_div_doc_4() {
    $("#done-rand-table tr").remove();
    document.getElementById("close_div_doc_but_4").style.display = "none";
}

function show_done_randevouz() {
    var doctorData = window.localStorage.getItem('doctorData');
    doctorData = JSON.parse(doctorData);
    document.getElementById("close_div_doc_but_4").style.display = "block";

    var url = "examinations/randevouz/getDoneRandevouz/" + doctorData.doctor_id
    sendXmlGetRequest(url, call_back_show_done_rand, call_back_error_show_done_rand)
}

function call_back_show_done_rand(response) {
    json = JSON.parse(response)
    console.log(json)
    $("#done-rand-table").removeClass("d-none")
    create_table_from_json_array(json, "done-rand-table")
}
function call_back_error_show_done_rand(response) {
    json = JSON.parse(response)
    console.log(json.response)
}

function close_div_doc_5() {
    document.getElementById("content").innerHTML = "";
    $("#content").addClass("d-none");
    document.getElementById("close_div_doc_but_5").style.display = "none";
    document.getElementById("compare-user-id").value = "";
}

function compare() {
    var doctorData = window.localStorage.getItem('doctorData');
    doctorData = JSON.parse(doctorData);

    document.getElementById("close_div_doc_but_5").style.display = "block";
    $("#content").removeClass("d-none");

    var user_id = $("#compare-user-id").val()
    var doctor_id = doctorData.doctor_id
    compare_exams(doctor_id, user_id)
    compare_bit = false
}

function compare_exams(doctor_id, user_id)
{
    const xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const obj = JSON.parse(xhr.responseText);
            document.getElementById("content").innerHTML = createCompareTable(obj);

        } else if (xhr.status !== 200) {
            document.getElementById('content').innerHTML = 'Request failed. Returned status of ' + xhr.status + "<br>"
                    +JSON.stringify(xhr.responseText);
        }
    };
    var URL = "http://localhost:8080/PersonalizedHealth/examinations/compareUsersDoneExams/" + doctor_id + "/" + user_id;
    xhr.open("GET", URL);
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
}

function
        createCompareTable(data) {
    var html = "<br><table style='border:2px solid white; background-color: rgb(51, 83, 109);'><tr><td>Date</td>";

    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].test_date + "</td>";
    }
    html += "</tr><tr><td>Blood Sugar</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].blood_sugar + "</td>";
    }
    html += "</tr><tr><td>Blood Sugar Level</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].blood_sugar_level + "</td>";
    }
    html += "</tr><tr><td>Cholesterol</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].cholesterol + "</td>";
    }
    html += "</tr><tr><td>Cholesterol Level</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].cholesterol_level + "</td>";
    }
    html += "</tr><tr><td>Iron</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].iron + "</td>";
    }
    html += "</tr><tr><td>Iron Level</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].iron_level + "</td>";
    }
    html += "</tr><tr><td>Vitamin D3</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].vitamin_d3 + "</td>";
    }
    html += "</tr><tr><td>Vitamin D3 Level</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].vitamin_d3_level + "</td>";
    }
    html += "</tr><tr><td>Vitamin B12</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].vitamin_b12 + "</td>";
    }
    html += "</tr><tr><td>Vitamin B12 Level</td>";
    for (var i = 0; i < Object.keys(data).length; i++) {
        html += "<td>" + data[i].vitamin_b12_level + "</td>";
    }
    html += "</tr></table><br>";
    return html;
}

function close_div_doc_6() {
    $("#chart_3d").html("");
    $("#chart_3d").addClass("d-none");
    document.getElementById("close_div_doc_but_6").style.display = "none";
}

function click_draw_chart() {
    var doctorData = window.localStorage.getItem('doctorData');
    doctorData = JSON.parse(doctorData);

    $("#chart_3d").removeClass("d-none");
    document.getElementById("close_div_doc_but_6").style.display = "block";

    var user_id = $("#compare-user-id").val()
    var doctor_id = doctorData.doctor_id
    drawChartFunc(doctor_id, user_id)
}

function drawChartFunc(doctor_id, user_id) {
    var URL = "http://localhost:8080/PersonalizedHealth/examinations/compareUsersDoneExams/" + doctor_id + "/" + user_id;
    sendXmlGetRequest(URL, draw_chart, call_back_error);
}

function draw_chart(response) {
    var json = JSON.parse(response);

    google.charts.load("current", {packages: ["corechart"]});
    google.charts.setOnLoadCallback(drawChart);

    var meas = $("#measurement").val();
    function drawChart() {
        var data = [];
        var Header = ['Date', 'Measure'];
        data.push(Header);
        for (var i = 0; i < Object.keys(json).length; i++)
        {
            var temp = [];
            temp.push(json[i].test_date);
            temp.push(json[i][meas]);
            data.push(temp);
        }
        var chartdata = new google.visualization.arrayToDataTable(data);
        var options = {
            title: 'Exams',
            is3D: true
        };
        $("#chart_3d").html("");
        var chart = new google.visualization.BarChart(document.getElementById('chart_3d'));
        chart.draw(chartdata, options);
    }
}

function call_back_error(response)
{
    console.log(response)
}

function close_div_doc_7() {
    $("#therapy_div").html("")

    $("#therapy_div").addClass("d-none");
    document.getElementById("close_div_doc_but_7").style.display = "none";
}

function show_therapies() {
    var doctorData = window.localStorage.getItem('doctorData');
    doctorData = JSON.parse(doctorData);

    $("#therapy_div").removeClass("d-none");
    document.getElementById("close_div_doc_but_7").style.display = "block";

    const xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const obj = JSON.parse(xhr.responseText);
            document.getElementById("therapy_div").innerHTML=""
            var i = 1;
            var count = Object.keys(obj).length;
            console.log(obj)
            for (id in obj) {
                document.getElementById("therapy_div").innerHTML += createTreatmentTable(obj[id], i);
                i++;
            }


        } else if (xhr.status !== 200) {
            
            console.log(xhr.responseText)
            js = JSON.parse(xhr.responseText)
            document.getElementById('therapy_div').innerHTML = js.response

        }
    };

    var user_id = $("#compare-user-id").val()

    var URL = "http://localhost:8080/PersonalizedHealth/examinations/Treatments/showTreatmentsDone/" + doctorData.doctor_id + "/" + user_id;

    xhr.open("GET", URL);
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send();
}

function send_and_store_treatment() {
    var doctorData = window.localStorage.getItem('doctorData');
    doctorData = JSON.parse(doctorData);

    var data = get_form_data_to_json("add_treat")
    data["doctor_id"] = doctorData.doctor_id
    console.log(data)
    url = "http://localhost:8080/PersonalizedHealth/examinations/Treatments/addTreatment"
    sendXmlPostRequest(url, data, call_back_send_treatment, call_back_error_send_treatment)
}
function call_back_send_treatment(response) {
    response = JSON.parse(response)
    $("#add-treatment-response").html(response.response)
}
function call_back_error_send_treatment(response) {
    response = JSON.parse(response)

    $("#add-treatment-response").html(response.response)
}
function createTreatmentTable(data, i) {
    var html = "<table style='border:2px solid white; background-color: rgb(51, 83, 109);'><tr><th>Category</th><th>Value</th></tr>";
    for (const x in data) {
        var category = x;
        var value = data[x];
        html += "<tr><td>" + category + "</td><td>" + value + "</td></tr>";
    }
    html += "</table><br>";
    return html;
}

function close_div_doc_8() {
    $("#add_treat").addClass("d-none")
    document.getElementById("close_div_doc_but_8").style.display = "none";
}

function add_treatment() {
    $("#add_treat").removeClass("d-none")
    document.getElementById("close_div_doc_but_8").style.display = "block";
}


