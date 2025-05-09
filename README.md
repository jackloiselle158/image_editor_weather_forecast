# image_editor_weather_forecast
Image Editor and Weather Forecast

This is a project I worked on as part of the mini project assignment for CSCI-C212 at Indiana University. Its main purpose is being an image editor useful for images of PPM file format. It can add filters to an image as well as rotate, mirror, and repeat the image as many times as the user wants. As part of a smaller portion of the project it has a weather forecast application that uses a weather API to get a 7-day forecast for the user given coordinates in latitude and longitude and a unit for temperature (celsius or fahrenheit). Portions of the project that aren't anything to do with the image reading, writing, and modifications were written by my instructors, Joshua Crotts and Muazzam Siddiqui.

HOW TO USE:
- This project was created using IntelliJ Community Edition, so I best recommend installing that first, or some version of IntelliJ in order to follow instructions properly.
- Once IntelliJ is installed and set up, unzip the contents of the project into a directory of your choosing.
- Open the project in IntelliJ and click "Trust Project"

IMAGE EDITOR
- Navigate to the ImageEditorRunner class and click the run button.

WEATHER FORECAST APPLICATION
- Navigate to the WeatherForecast class
- Click on the three dots next to the run buttons and click "edit"
- Click the '+' button, then "Application", then type in the name of the class, which is WeatherForecast
- Click apply, then go back to the edit configuration screen.
- Input terminal arguments with a '--' to start, then the word, then a space, then the value. Valid arguments are latitude, longitude, and unit.
- Click run
