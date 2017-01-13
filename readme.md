# KSP Orbital Kalculator

## What is it?

KSP Orbital Kalculator is a utility designed to assist players of the videogame Kerbal Space Program in travelling between planets. The application allows the user to input the planet they wish to start from, the planet they wish to travel to, and the height of the orbit they will be starting from. The application then calculates the required angles, change in velocity, and escape velocity for the transfer maneuver between the two planets. Finally, the application displays the calculated information numerically on the main interface, and graphically on the two angle display interfaces.

## Background

### What is Kerbal Space Program?

Kerbal Space Program is a management and space flight simulation gaming developed by Squad. In the game, the player is in charge of running a space program of little green men called Kerbals, from the planet Kerbin. Players design and pilot spacecraft, which can be piloted to perform missions. Missions will often involve travelling to other locations, either on the planet Kerbin, in orbit, or on other bodies (e.g. planets or moons) in the Kerbol system.

### What is a Hohmann transfer maneuver?

A Hohmann transfer orbit is a half elliptical orbit used to transfer a spacecraft from one circular orbit of a central body into a different circular orbit of the same body. A Hohmann transfer maneuver is an orbital maneuver which consists of two impulses. First, an impulse is used to change the spacecraft's circular orbit into an elliptical orbit which intersects with the desired orbit. Once the spacecraft travels along its Hohmann orbit to reach the intersection with the desired orbit, a second impulse is used to circularize the orbit.

A Hohmann transfer can be used to move from a lower orbit into a higher orbit, or vice-versa. A variation of the Hohmann transfer maneuver can be used to transfer between orbits of two bodies on different orbits around the same central body. The KSP Orbital Kalculator is designed to assist with this maneuver.

## The Application

### Tabbed Interface

The interface used for switching between the different interfaces of the application is a bottom navigation bar, as specified in Google's Material Design guidelines. The bar shows icons and labels for each of the app's three main screens. The current screen's icon and label are highlighted. Tapping on one of the other tab icons causes the application to switch to that screen. The bottom bar in this application was implemented using roughike's BottomBar library Version 2.0.

### Calculator View

<img src="/graphics/CalculatorKerbinToDuna.png" width="360">

The calculator screen is where the user inputs the orbital parameters to be used in calculating the orbital maneuver. The user selects an origin and a destination planet using the two spinners, then inputs a parking orbit height in the numeric text input field. If the user inputs the same planet for the origin and the destination, or doesn't input a positive integer for the parking orbit height, then an error message will appear in the center of the interface. If there were no errors in the user input, then after pressing the calculate button, the phase angle, ejection angle, ejection velocity, and required delta-V are listed in the text views at the bottom of the calculator screen. The other views will also be updated to dispay this information graphically. The reset button can be pressed to clear the text views and edit text field of any text, as well as resetting the other views.

### Phase Angle Display View

<img src="/graphics/PhaseKerbinToDuna.png" width="360"> <img src="/graphics/PhaseKerbinToEve.png" width="360">

The phase screen is where the user can see a graphical display of the phase angle; the between the destination and origin planets required to initiate the Hohmann transfer maneuver. When the application is first started up, or after the reset button is pressed, the phase screen will display a message saying there is no calculation data to display. When the phase view is selected after a calculation is performed, the screen will show a display of the selected origin and destination planets, and the angle between them. The origin planet will be located along the horizontal centerline of the screen, while the destination planet will be offset by the calculated phase angle. The display is drawn as though viewed from above the sun's north pole, such that the directions of the planets' orbits are counter clockwise. 

### Ejection Angle Display View

<img src="/graphics/EjectKerbinToDuna.png" width="360"> <img src="/graphics/EjectKerbinToEve.png" width="360">

The eject screen is where the user can see a graphical display of the ejection angle; the angle along the spacecraft's orbit of the origin planet at which it performs the impulse used to carry the craft into the Hohmann transfer orbit. The origin planet is shown in the center of the screen. The spacecraft is represented by a grey triangle. An arrow indicates the direction of the spacecraft's orbit around the origin planet. The display is drawn as though viewed from above the origin planet's north pole, with the top side of the screen pointing in the direction of the planet's orbit around the sun and the bottom side of the screen pointing in the direction opposite the planet's orbit around the sun.
