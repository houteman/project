        // define the svg size
        var margin = {top: 80, right: 100, bottom: 100, left: 100};
        var outer_width = 1100;
        var outer_height =900;
        var svg_width = outer_width - margin.left - margin.right;
        var svg_height = outer_height - margin.top - margin.bottom;
        var myData1=[];

        // Initialising display_year
        display_year = null;

        var selected_country;
        var selected_countries = [];

        var playInterval;
        var myStopFunction;

        d3.select("#play_button")
            .on("click",function(){
                playInterval = setInterval(function(){
                    display_year++;

                if (display_year>2017) {
                    display_year=2007;
                }

                generateVis();

                yearTitleElement.innerHTML = display_year; 800;
                },700);
            });

            d3.select("#stop_button")
            .on("click",function(){
            myStopFunction= clearInterval(playInterval); 
        });


        //Queue to change and store click_countries;
        function addCountry(country){
            if (selected_countries.length < 2){
                selected_countries.push(country);
                
            }
            else{
                console.log(selected_countries);
                selected_countries.pop();
                selected_countries.unshift(country);
            }
            document.getElementById("Countries").innerHTML=selected_countries;
        }
        function makeBarChart(selected_countries){
            if(selected_countries.length==0){
                
            }
            else if (selected_countries.length==1){
                barChart(selected_countries);
            }
            else{
                barChart(selected_countries);
                comparisonBarChart(selected_countries);
            }
        }


        function yearFilter(value){
            return (value.Year == display_year)
        }

        var yearTitleDiv = document.getElementById("YearDiv");
        var yearTitleElement = document.getElementById("Year");
        yearTitleElement.style.color = "grey";
        yearTitleElement.style.fontSize = "200px";
        yearTitleElement.style.opacity = ".6";
        yearTitleElement.style.fontFamily = "Roboto";
        yearTitleDiv.style.zIndex = "-10";
        yearTitleDiv.style.position = "absolute";
        yearTitleDiv.style.left = "300px";
        yearTitleDiv.style.top = "400px";

        //Load csv_data
        d3.csv("./GCI_CompleteData4.csv")
          .then(function(data) {
                  myData1 = data;

                    yScale1.domain([1, d3.max(myData1, function(d){ return +d.Global_Competitiveness_Index})])
                    svg1.select("#y_axis").call(yAxis);
                    
                    xScale1 .domain([1, d3.max(myData1, function(d){   return +d.GDP})])
                    svg1.select("#x_axis").call(xAxis);

                  min_year = d3.min(myData1.map(function(d){ return +d.Year}));
                  max_year = d3.max(myData1.map(function(d){ return +d.Year}));
                  console.log("min_year/max_year:", min_year, max_year);
                   display_year = min_year;
        
                // Calling main visualisation function
                   generateVis();
        })


        // Create svg canvas
        var svg1 = d3.select("body")
                                .append("svg")
                                .attr("width", svg_width + margin.left + margin.right)
                                .attr("height", svg_height + margin.top + margin.bottom)
                                .append("g")
                                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        // create y scale
        var yScale1 = d3.scaleLinear()
                                  .domain([0, d3.max(myData1, function(d){ return +d.Global_Competitiveness_Index})])
                                  .range([svg_height, 0]);

        // create x scale
        var xScale1 = d3.scaleLog()
                                .domain([0, d3.max(myData1, function(d){   return +d.GDP})])
                                .range([0, svg_width]);

        // create scale for the radius of each circle
        var radiusScale = d3.scaleSqrt()
                                    .domain([0, 5e8])
                                    .range([0, 50]);

        // Creating color scale
        var colourScale = d3.scaleOrdinal(d3.schemeCategory10);


        //define Y axis
        var xAxis = d3.axisBottom()
                            .scale(xScale1)
                            .ticks(5);

        xAxis.tickFormat(d3.format(".0s"));

        //define Y axis
        var yAxis = d3.axisLeft()
                            .scale(yScale1)
                            .ticks(10)

        //call xAxis
         svg1.append("g")
                    .attr("class","axis")
                    .attr("id","x_axis")
                    .attr("transform","translate(0,"+svg_height+")")
                    .call(xAxis);

        // call yAxis
        svg1.append("g")
                    .attr("class","axis")
                    .attr("id","y_axis")
                    .call(yAxis);

        // Add axis labels
        svg1.append("text")
                    .attr("class", "axis")
                    .attr("x", svg_width)
                    .attr("y", svg_height);

        var drag = d3.drag();

        function generateVis(){
       
       // Filter the data to only include the current year
        var filtered_dataset = myData1.filter(yearFilter);

        var circles = svg1.selectAll("circle")
                                    .data(filtered_dataset);
    
         var texts = svg1.selectAll(".countrylabel")//?????????????????
            .data(filtered_dataset, function key(d){return d.Country});

        //join the circles (bubbles)
        circles.enter()
                   .append("circle")
                   .attr("cx", function(d){console.log("circle"); return xScale1(+d.GDP)})
                   .attr("cy", function(d){ return yScale1(+d.Global_Competitiveness_Index)})
                   .attr("r", function(d){return radiusScale(d.Population)})
                   .attr("fill", function(d){ return colourScale(d.Region)})
                   .style("opacity", .5)        
                   .on("click", function (d) {
                    addCountry(d.Country);
                   makeBarChart(selected_countries);
        })
                    .transition()
                    .duration(1000)
        

        texts.enter()
                .append("text")
                .attr("x", function(d){return xScale1(+d.GDP)})
                .attr("y", function(d){return yScale1(+d.Global_Competitiveness_Index)})
                .attr("fill", "black")
                .attr("class", "countrylabel")
                //.attr("font-size",15)
                 .attr("font-size", function(d){return radiusScale(d.Population*0.08)})
                 .text(function(d){return d.Country})
                 .on("click", function (d) {
                    addCountry(d.Country);
                    makeBarChart(selected_countries);
                })
                 .text(function(d){return d.Country});
               


            //update the value
        circles.transition()
                    .duration(1000)
                    .attr("cx", function(d){ return xScale1(+d.GDP) })
                    .attr("cy", function(d){ return yScale1(+d.Global_Competitiveness_Index)})
                    .attr("r", function(d){ return radiusScale(+d.Population)})
                    .style("fill", function(d){ return colourScale(d.Region)})
                    .style("opacity", .5)
                    .transition()
                     .duration(1000);


        texts.transition()
                    .duration(1000)
                    .attr("x", function(d){return xScale1(+d.GDP)})
                    .attr("y", function(d){return yScale1(+d.Global_Competitiveness_Index)});

        circles.exit()
                    .transition()
                    .duration(200)
                    .ease(d3.easeBounce)
                    .attr("r", 0)
                    .remove();


        texts.exit().remove();

        d3.select("#year_header").text("Year: " + display_year)

        setInterval(function(){
        yearTitleElement.innerHTML = display_year; 800;
        }, 200)
        console.log(display_year);
        console.log(yearTitleElement);
    }


    function click_2007(){
         display_year = min_year;
         generateVis();
    }

    function click_2008(){
        display_year = min_year+1;
         generateVis();

    }

     function click_2009(){
        display_year = min_year+2;
         generateVis();

    }
         function click_2010(){
        display_year = min_year+3;
         generateVis();

    }
         function click_2011(){
        display_year = min_year+4;
         generateVis();

    }
         function click_2012(){
        display_year = min_year+5;
         generateVis();

    }
         function click_2013(){
        display_year = min_year+6;
         generateVis();

    }
         function click_2014(){
        display_year = min_year+7;
         generateVis();

    }
         function click_2015(){
        display_year = min_year+8;
         generateVis();

    }
         function click_2016(){
        display_year = min_year+9;
         generateVis();

    }
         function click_2017(){
        display_year = min_year+10;
         generateVis();

    }

    //trace function
        function showtrace(){
        if(document.getElementById("traces").checked){
            console.log("traces checked");
            if(selected_countries.length<2){
                console.log("generate trail for 1 called");
                generateTrail(selected_countries[0]);
            }
            else{
                generateTrail(selected_countries[0]);
                generateTrail(selected_countries[1]);
            }
        }
        else{
            console.log("false");
            generateVis();
        }
    }

    function generateTrail(country) {
        console.log("country ",country);
        function countryFilter(value) {
            return (value.Country == country);
        }
        var trail_data = myData1.filter(countryFilter);
        console.log(trail_data);
        
        var trace = svg1.selectAll("circle")
           .data(trail_data);
       }