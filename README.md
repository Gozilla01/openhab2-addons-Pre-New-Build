# openhab2-addons-Pre-New-Build


### Index
- [Bus and Channels](#bus-and-channels)
- [Test Lighting group addresses](#test-lighting-group-addresses)
- [Test Automation group addresses](#test-automation-group-addresses)
- [Changelog](#changelog)
- [Group Example](#group-example)
- [Bus Command Example](#bus-command-example)
- [Bus Motion Detector Example](#bus-motion-detector-example)

### Bus and Channels

Devices support some of the following channels:

| Bus Name          |  Description bus       |Parameter</br>Type</br>Description       |Channel</br>Type (Read/Write)</br>Description | 
|-------------------|------------------------|-------------------------|--------------------------|
|`bus_on_off_switch`   |WHO=1 Lightning |`where`</br>String</br>Value where OWN</br>`what`</br>Integer (default = 0)</br>Value what OWN</br>`addrtype`</br>Integer (default = 1)</br>Address type 1= Point to Point, 2= Area, 3= Group, 4= General| `switch`</br>Switch (R/W)</br>To switch the device ON/OFF                |
|`bus_automation`   |WHO=2 Automation |`where`</br>String</br>Value where OWN</br>`addrtype`</br>Integer (default = 1)</br>Address type 1= Point to Point, 2= Area, 3= Group, 4= General| `shutterPosition`</br>Rollershutter (R/W)</br>To activate roller shutters (UP, DOWN, STOP, Percent)</br>`shutterMotion`</br>Integer (R)</br>Roller shutter movement 0= stop, 1= up, 2= down               |
|`bus_on_off_aux`   |WHO=9 command auxiliary |`where`</br>String</br>Value where OWN| `switch`</br>Switch (R/W)</br>To switch the device ON/OFF                |  
|`bus_motion_detector`   |WHO=1 motion decector</br>(movement and lux value detected) |`where`</br>String</br>Value where OWN</br>`automaticToOff`</br>Boolean</br>Automatic `OFF` after 2 seconds (default=false)| `switch`</br>Switch (R/W)</br>To switch the device ON/OFF</br>`value`</br>Integer (R)</br>Detected lux value |
|`bus_command`   |command management |`who`</br>String</br>Value who OWN</br>`what`</br>String</br>Value what OWN for ON</br>`whatOff`</br>String</br>Value what OWN for OFF</br>`compare`</br>String</br>Code OWN for comparison|  `switch`</br>Switch (R/W)</br>To switch the device ON/OFF</br>`contact`</br>Switch (R/W)</br>To switch the device OPEN/CLOSE</br>`what`</br>String (W)</br>Set the value what OWN               |  

### Test Lighting group addresses
last test upgrade pre7

| Group Command | Discovery Bus Principale | Bus Principal      | Discovery Bus Local | Bus Local      | Note                    | 
| ------------- |:------------------------:|:------------------:|:-------------------:| :------------: | ----------------------- |
| Area          |`OK`                      | `OK`               | `OK`                | `Problem (1)`  | **`(1)`**  requestTurnOn and requestTurnOff invalid integer format      |
| Group         |`OK`                      | `Problem (1)`      | `OK`                | `Problem (1)`  | **`(1)`**  requestTurnOn and requestTurnOff invalid integer format      |
| General       |`OK`                      | `OK (2)`           | `OK`                | `Problem (1)`  | **`(1)`**  requestTurnOn and requestTurnOff allowed value [0]</br>**`(2)`** By sending the command, the group and area handlers are also called, `correct ???`

### Test Automation group addresses
last test upgrade pre7

| Group Command | Discovery Bus Principale | Bus Principal    | Discovery Bus Principale | Bus Local      | Note                      | 
| ------------- |:------------------------:| :--------------: |:------------------------:| :------------: | ------------------------- |
| Area          | `OK`                     | `OK`             | `OK`                     | `Problem (1)`  | **`(1)`**  requestMoveUp and requestMoveDown invalid integer format                           |
| Group         | `OK`                     | `Problem (1)`    | `OK`                     | `Problem (1)`  | **`(1)`**  requestMoveUp and requestMoveDown invalid integer format   |
| General       | `OK`                     | `OK (2)`         | `OK`                     | `Problem (1)`  | **`(1)`**  requestMoveUp and requestMoveDown allowed value [0]</br>**`(2)`** By sending the command, the group and area handlers are also called, `correct ???`   |

### Changelog

**v2.5.0.M3.pre10**  IN PROGRESS dd/07/2019

- Update for bus `bus_motion_detector`
- Temporary modification to the `message.Lighting`
  - Added management for new timed WHAT
  - Correction for group command management for different `Lighting.Type`
  - Added management for `bus_motion_detector`
- Temporary modification to the `message.Automation`
  - Correction for group command management for different `Automation.Type`

**v2.5.0.M3.pre9** 19/07/2019

- Update for bus `bus_motion_detector`
  - Update request
  - Added OFF state management (WHAT = 39)
  - Added management of commands from OH to bus (ON WHAT=34 and OFF WHAT=39)
  - Addes parameter `automaticToOff`
  - Other minor corrections
  - **TO DO** Delete the `MOVEMENT_DETECTED` and `END_MOVEMENT_DETECTED` management from `OpenWebNetLightingHandler` ?

**v2.5.0.M3.pre8** 07/07/2019

- Add new bus `bus_motion_detector` for motion decector (movement and lux value detected)
  - Tested with type `BMSE-3003` , `BMSE-1001` and `048834` 
  - Discovered only with `discoveryByActivation = true`
  - Request lux value with the initialization of things
  - Parameter `where`
  - Channel `switch` state `ON` to the movement and after 2 seconds automatically `OFF`
  - Channel `value` numeric with detected value lux
- Update BusAuxToggle.xml

**v2.5.0.M3.pre7** - 29/06/2019

- Fix bug `normalizeWhere` for group command with different local bus
- Fix bug `isArea` for group command with different local bus
- Fix bug `isGroup` for group command with different local bus
- Fix bug `isGeneral` for group command with different local bus
- Fix bug `isCompareALocalBus` for group command with different local bus
- Fix bug `isCompareLocalBus` for group command with different local bus

**v2.5.0.M3.pre6** - 23/06/2019

- Fix bug `ownId` for bus Command

**v2.5.0.M3.pre5** - 23/06/2019

- Update channel `what` for bus Command
- Add example for bus Command
  
**v2.5.0.M3.pre4** - 22/06/2019

- Update normalizeWhere
- [FIX [#52](https://github.com/mvalla/openhab2-addons/issues/52) and [#22](https://github.com/mvalla/openhab2-addons/issues/22)] Add bus Command
  - **Not fully functional, waiting to resolve the library implementation**
  - **TO DO** file update README.MD
  
**v2.5.0.M3.pre3** - 20/06/2019

- Renamed the name of the version from 2.5.0.M4 to 2.5.0.M3
- After commit cdc8ba1
  - [FIX [#76](https://github.com/mvalla/openhab2-addons/issues/76)] Migrated to new OH2 build system (bndtools)
  - Now using openwebnet-lib-0.9.20

**v2.5.0.M4.pre2** - 16/06/2019

- [Issue [#35](https://github.com/mvalla/openhab2-addons/issues/35)] Support for group addresses (WHERE=#n)
   - For lighting and automation
   - New parameter `addrtype` (`1`= Point to Point, `2`= Area, `3`= Group, `4`= General)
   - **TO DO** file update README.MD
   - See the tables [Lighting group addresses](https://github.com/Gozilla01/openhab2-addons-Pre-New-Build/blob/master/README.md#test-lighting-group-addresses) and [Automation group addresses](https://github.com/Gozilla01/openhab2-addons-Pre-New-Build/blob/master/README.md#test-automation-group-addresses)
- [Issue [#79](https://github.com/mvalla/openhab2-addons/issues/79)] Add what parameter to lighting
   - New parameter `what` (default `what`= 0)  
   - **Not working, waiting to resolve the library implementation**
   - **TO DO** file update README.MD

**v2.5.0.M4.pre1** - 08/06/2019

- From [v2.5.0.M3](https://github.com/mvalla/openhab2-addons/blob/openwebnet/bundles/org.openhab.binding.openwebnet/README_beta.md#changelog)
- [FIX [#80](https://github.com/mvalla/openhab2-addons/issues/80)] thermo: setmode in MANUAL not work
- [FIX [#12](https://github.com/mvalla/openhab2-addons/issues/12) and [#32](https://github.com/mvalla/openhab2-addons/issues/32)] Add bus AUX
   - **TO DO** file update README.MD
- [FIX [#63](https://github.com/mvalla/openhab2-addons/issues/63)] Rollershutter items do not track changes in movement from external commands
   - *[BREAKING CHANGE]* canale `shutter` changed the name to `shutterPosition`
   - New channel `shutterMotion` (`0`= stop, `1`= up, `2`= down) 
   - **TO DO** file update README.MD
- [Fix [#69](https://github.com/mvalla/openhab2-addons/issues/69)] Updating of article statuses, lighting and automation, with the received AMB-GR-GEN commands.



### Group Example

**openwebnet.things:**

```xtend
Bridge openwebnet:bus_gateway:mybridge "MyHOMEServer1" [ host="192.168.1.35", passwd="abcde", port=20000, discoveryByActivation=true ]
{  
      bus_on_off_switch MygroupArea    "Group 1-2 Area"		[ where="2", what=0, addrtype=2 ]
      bus_on_off_switch MygroupGen     "Group 1-GEN"		[ where="0", what=0, addrtype=4 ]
}
``` 

**openwebnet.items:**

```xtend
Switch   ILR_testArea1   "Group 1-2 AREA"   { channel="openwebnet:bus_on_off_switch:mybridge:MygroupArea:switch"}
Switch   ILR_testGen1    "Group 1-GEN"      { channel="openwebnet:bus_on_off_switch:mybridge:MygroupGen:switch"}

```

**openwebnet.sitemap**

```xtend
sitemap openwebnet label="OpenWebNet Binding Example Sitemap"
{
   Frame label="Test AMB Lighing" 
   {     
      Switch item=ILR_testArea1     label="Area 2"    mappings=[ON="ON"]
      Switch item=ILR_testArea1     label="Area 2"    mappings=[OFF="OFF"]		   
   }
   Frame label="Test GEN Lighing"
   {
      Switch item=ILR_testGen1      label="GEN"       mappings=[ON="ON"]
      Switch item=ILR_testGen1      label="GEN"       mappings=[OFF="OFF"]		
   }
}

```

### Bus Command Example

**openwebnet.things:**

```xtend
Bridge openwebnet:bus_gateway:mybridge "MyHOMEServer1" [ host="192.168.1.35", passwd="abcde", port=20000, discoveryByActivation=true ]
{  
      bus_command   Mycomm     "Command"       [ who ="1" , what="1" , where = "22" ,  whatOff="0", compare ="" ]
      bus_command   Mycomm1    "Command 1"     [ who ="1" , what="1" , where = "21" ,  whatOff="0", compare ="*1*1*23##" ]
      bus_command   Mycomm2    "Command 2"     [ who ="2" , what="1" , where = "81" ,  whatOff="0", compare ="*1*1*23##" ]
}
``` 

**openwebnet.items:**

```xtend
String       iMyCommand       { channel="openwebnet:bus_command:mybridge:Mycomm:what" }
Switch       iMyCommand1      { channel="openwebnet:bus_command:mybridge:Mycomm:switch"}
Contact      iMyCommand2      { channel="openwebnet:bus_command:mybridge:Mycomm:contact"}

```

**openwebnet.sitemap**

```xtend
sitemap openwebnet label="OpenWebNet Binding Example Sitemap"
{
   Frame label="Command" 
   {
     Text 	 item=iMyCommand   label="What [%s]"      icon="door"
     Switch      item=iMyCommand1  label="Switch [%s]"    icon="light"
     Text 	 item=iMyCommand2  label="Contact [%s]"   icon="door"
     Switch      item=iMyCommand1  label="da Button [%s]" icon="light"  mappings=[ON="ON" , OFF="OFF"]     
   }
}

```

**openwebnet.rules**

```xtend
// Scenario: Setting channel what
rule "testCommand"
when
        Item Luce_entrata changed 
then
        switch(Luce_entrata.state ) {
            case ON: {
               logInfo("Test rules testCommand", "success! ON" )
               iMyCommand.sendCommand("11")
            }
            case OFF: {
               logInfo("Test rules testCommand", "success! OFF" )
               iMyCommand.sendCommand("0")
            }
        }
           
end

```

### Bus Motion Detector Example

**openwebnet.things:**

```xtend
Bridge openwebnet:bus_gateway:mybridge "MyHOMEServer1" [ host="192.168.1.35", passwd="abcde", port=20000, discoveryByActivation=true ]
{  
      bus_motion_detector   Mymotion     "Motion"       [ where = "22" ,  automaticToOff = false]
}
``` 

**openwebnet.items:**

```xtend
Switch       iMymotion      { channel="openwebnet:bus_command:mybridge:Mymotion:switch"}
Number       iMymotion      { channel="openwebnet:bus_command:mybridge:Mymotion:value"}

```
