# Parking-Management-System
KLE Society's
KLE Technological University

DBA Course Project Report
On
PARKING MANAGEMENT SYSTEM
Database Applications Lab (15ECSP204)
Database Management System (15ECSC208)
Submitted By
NAME	 			ROLLNO			USN
  AKASH KURUTAGI	      202			01FE19BCS075   
  SUSHEN ITAGI		      216		      01FE19BCS089   
  VISHAL GIRADDI		      214			01FE19BCS087   
  PRAJWAL BASTI	            206			01FE19BCS079   
TEAM NUMBER: 02(B1)
Faculty In-charge:
Sunita
SCHOOL OF COMPUTER SCIENCE & ENGINEERING
HUBLI – 580 031 (India)
Academic year 2020-21

                                                         
PARKING MANAGEMENT SYSTEM


CONTENTS
1.	 Introduction	          
2.	 Objectives	

     2.1     Data collection
     
     2.2      Data design
     
     2.3      Implementation
3.	 Requirements	

     3.1.     Data Requirements	
     
     3.2.     Functional Requirements	
     
4.	 Design Phase	

     4.1.     E-R Diagram	
     
     4.2.     Relational Schema
5.	 Normalization

     5.1.	   E-R Diagram after Normalization	
                
     5.2.     Relational Schema after Normalization
             	
1.INTRODUCTION:

Structure of parking lots 
A typical parking floor consists of one or more parking lots that are further subdivided 
into spots. A floor usually has a height limit that restricts certain vehicles from entering the 
lot so we partition the floors based on Vehicle type . Additionally, a floor contains several uniquely
numbered parking slots.And on departure of the vehicle based on the entry time and the exit time 
the parking ticket is generated and the corresponding spot is made free.
 
Regular customers are usually given pass/stickers to place  on their dashboard or windshield so the parking lot management can easily determine that the customers 
are not in violation of any parking rules.
Walkin customers have to pay for slots so they can park their vehicles in
the same designated slots.Walk-in customers are given a parking slot and then assigned to the customer
as the slip is generated, based on preferences they have specified

PROBLEM STATEMENT: 
• The traditional parking system is time insufficient. 
• The payment process slows down the entry and exit of the other vehicles. 
• Only cash payment can be used in practice. 
• Parking slots can be filled prior to visiting the slot. 
The parking management system is used to help managing the vehicles allocated position, avoid congestion and collect the required amount in defined manner. The system can be widely used where there are many meeting points like many shopping complexes or like megamalls

2.OBJECTIVES

Design a efficient semi automated parking system that
1. Displays available parking slots in a systematic manner according to their type i.e  two wheeler slot, 4 wheeler slot  
2. collects vehicle information such as vehicle type, vehicle registration number, arrival time etc
3. Ask the user about their preferred method of payment i.e cash or card
4. Calculate the amount to be paid using arrival time and departure time
5. Processes card payments
6. Reserve special slots with requisite charging system for EV’s
7.  Reserve a certain number of parking slots for shop owners to park their vehicle
8. Store user information to quickly process entry and exit of regular customer

2.1 DATA COLLECTION
Types of data required for parking lots to operate, we must know more about the types of people who visit parking lots.
 Customers who enter parking lots belong to one of the following groups:
•A regular customer who has purchased a biweekly, monthly, or yearly pass.
•A walk-in customer who neither has a pass nor booked a slot remotely. A slot will be assigned to such a customer based on availability.

2.2 DATA DESIGN:
To establish efficient parking system we have to store and process the data effectively 
that wolud be working with following main entities:
•Parking lot
•Customer
•Parking reservation
Entities and the attributes
 	1. PARKING_LOT : P_ID, IS_SLOT_AVAILABLE, IS_REENTRY_ALLOWED, IS_VALET_PARKING_AVAILABLE
 	2. PARKING_SLOT : F_ID,PS_ID,SLOT_NUMBER,Is_slot_full
 	3.FLOOR : P_ID,F_ID,NUMBER_OF_SLOTS,IS_SLOTS_FULL,IS_RESERVED
 	4.ELECTRICAL_PARKING_SLOT : F_ID,CUST_ID,EV_ID,CHARGING_PREFERENCE
 	5.CUSTOMER :   CUST_ID,VEHICLE_NUMBER,CONTACT_NUMBER,REGISTRATION_DATE,IS_ELECTRIC,IS_REGULAR_CUSTOMER
 	6.REGULAR_CUSTOMER : CUST_ID,REG_ID,PURCHASE_DATE,EXPIRY_DATE,COST
 	7. RESERVATION : CUST_ID, R_ID,PS_ID, BOOKIN_DATE, DURATION_IN_MINUTES
 	8. PARKING_SLIP : R_ID, S_ID, ACTUAL_ENTRY_TIME, ACTUAL_EXIT_TIME, PENALTY_TYPE, PENALTY_COST, BASIC_COST
 	9. PAYMENT : PY_ID,S_ID, CUST_ID, PER_HOUR_CHARGES, E_CHARGING_PER_HOUR, MODE, COST
 	
 	Relationship
 	1. FLOOR has PARKING LOT
2.FLOOR has ELECTRIC_PARKING_LOT, PARKING_SLOT
 	3. PARKING_SLOT has RESERVATION
 	4. RESERVATION generates PARKING_SLIP
 	5. CUSTOMER makes RESERVATION
 	6.REGULAR_CUSTOMER belongs to CUSTOMER
 	7.CUSTOMER makes PAYMENT


2.3 IMPLEMENTATION:

As a part of our implementation we are using RDBMS To store the required data and to access the information, this is about the backend part and for front end part we are using HTML for the body and structure ,CSS for styling and design and lastly Javascript for the compling the information.


3.REQUIREMENTS
For our Parking management system our main requirement is to deal about how to handle the payment criteria 
based on the type of the vehicle entering , Should be alloted to the specific floor and spot.


3.1 DATA REQUIREMENTS:
* Is the vehicle a reserved vehicle or the walk-in vehicle.
* Is the vehicle an electric vehicle, if yes, is charging point preferred or not.
* For regular customers the parking pass start date and the expiry date is checked.
* And for normal customers the vehicle number, phone number is noted.
* And on successful reservation the date is noted and the entry time and the exit time is noted.

3.2 FUNCTIONAL REQUIREMENTS:
* When the vehicle enters the parking lot the reservation is checked, if reserved the already 
allotted spot is allotted,
 if not, the availability is checked based on type of vehicle.
* If available the corresponding slot in the particular floor is allotted.
* And on exit of the vehicle the parking slip is generated stating the entry time and the exit time 
and other details.
* And based on entry time and exit time and charges are calculated 
* And to this calculated amount the penalty, electric charging rate is added if applies.
* And on successful payment by their preferred payment mode, the spot is made available for next 
Parking









4.DESIGN PHASE

4.1 ER-MODEL









































4.2 RELATIONAL SCHEMA

PARKING_LOT
P_ID
IS_SLOT_AVAILABLE	IS_REENTRY_ALLOWED	IS_VALET_PARKING_AVAILABLE



                                                                  
PARKING_SLOT
F_ID
PS_ID	SLOT_NUMBER	IS_SLOT_FULL


FLOOR
P_ID
F_ID
NUMBER_OF_SLOTS	IS_FLOOR_FULL	IS_RESERVED


CUSTOMER
CUST_ID
PS_ID	VEHICLE_NUMBER	CONTACT_NUMBER	REGISTRATION_DATE	IS_VEHICLE_ELECTRIC	IS_REGULAR_CUSTOMER

ELETRICAL_PARKING_SLOT
EV_ID	CUST_ID	F_ID
CHARGING_PREFERENCE


REGULAR_CUSTOMER
CUST_ID
REG_ID	PURCHASE_DATE	EXPIRY_DATE	COST


RESERVATION
CUST_ID
R_ID	          PS_ID	BOOKIN_DATE	DURATION_IN_MINUTES


PARKING_SLIP
R_ID
S_ID
ACTUAL_ENTRY_TIME	ACTUAL_EXIT_TIME	PENALTY_TYPE	PENALTY_COST	BASIC_COST


PAYMENT
PY_ID	S_ID
PER_HOUR_CHARGES	E_CHARGING_PER_HOUR	MODE	COST	CUST_ID




5.NORMALIZATION
1.	PARKING_LOT, FLOOR, CUSTOMER, ELECTRICAL_PARKING_SLOT, REGULAR_CUSTOMER, RESERVATION, PARKING_SLIP, PAYMENT.
         Since above mentioned tables doesn’t contain any multi valued attributes it’s in 1NF. 
2.	PARKING_SLOT

F_ID	PS_ID	SLOT_NUMBER	IS_SLOT_FULL

       The table PARKING_SLOT we have multi-key attributes where non key attribute depends on those key attributes.
      This entity violates 2NF. Therefore, we have to consider two different tables. 
1.	PARKING_SLOT {PS_ID, SLOT_NUMBER, IS_SLOT _FULL}
2.	FLOOR_DETAILS {F_ID, PS_ID}

3.	Schema is already in 3NF because, only key is determining the other attributes. Hence the schema remains same after normalization.
















































5.1 ER-MODEL AFTER NORMLISATION











































5.2 RELATIONAL SCHEMA AFTER NORMALISATON

PARKING_LOT
P_ID
IS_SLOT_AVAILABLE	IS_REENTRY_ALLOWED	IS_VALET_PARKING_AVAILABLE



                    PARKING_SLOT
PS_ID
SLOT_NUMBER	IS_SLOT_FULL

FLOOR
P_ID
F_ID
NUMBER_OF_SLOTS	IS_FLOOR_FULL	IS_RESERVED


FLOOR_DETAILS
F_ID
PS_ID


CUSTOMER
CUST_ID
PS_ID	VEHICLE_NUMBER	CONTACT_NUMBER	REGISTRATION_DATE	IS_VEHICLE_ELECTRIC	IS_REGULAR_CUSTOMER

ELETRICAL_PARKING_SLOT
EV_ID	CUST_ID	F_ID
CHARGING_PREFERENCE


REGULAR_CUSTOMER
CUST_ID
REG_ID	PURCHASE_DATE	EXPIRY_DATE	COST


RESERVATION
CUST_ID
R_ID	          PS_ID	BOOKIN_DATE	DURATION_IN_MINUTES


PARKING_SLIP
R_ID
S_ID
ACTUAL_ENTRY_TIME	ACTUAL_EXIT_TIME	PENALTY_TYPE	PENALTY_COST	BASIC_COST


PAYMENT
PY_ID	S_ID
PER_HOUR_CHARGES	E_CHARGING_PER_HOUR	MODE	COST	CUST_ID





















    THANK
       YOU              
