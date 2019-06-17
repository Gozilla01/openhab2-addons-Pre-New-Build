# openhab2-addons-Pre-New-Build

## Changelog

**v2.5.0.M3.pre3 =IN PROGRESS=** - dd/06/2019

- Renamed the name of the version from 2.5.0.M4 to 2.5.0.M3
- After commit cdc8ba1
- [FIX [#76](https://github.com/mvalla/openhab2-addons/issues/76)] Migrated to new OH2 build system (bndtools)
- Now using openwebnet-lib-0.9.20

**v2.5.0.M4.pre2** - 16/06/2019

- [Issue [#35](https://github.com/mvalla/openhab2-addons/issues/35)] Support for group addresses (WHERE=#n)
   - For lighting and automation
   - New parameter `addrtype` (`1`= Point to Point, `2`= Area, `3`= Group, `4`= General)
   - **TO DO** file update README.MD
   
   - **Test Lighting group addresses**

     | Group Command | Bus Principal    | Bus Local      | Note                             | 
     | ------------- | :--------------: | :------------: | -------------------------------- |
     | Area          | `OK`             | `No Test`      |                                  |
     | Group         | `Problem (1)`    | `No Test`      | **`(1)`** OwnId problem creation, see bridgeHandler.ownIdFromWhoWhere().<br>Es. `1.1` instead of `1.#1`       |
     | General       | `OK (1)`         | `No Test`      | **`(1)`** By sending the command, the group and area handlers are also called, `correct ???`                                  |

   - **Test Automation group addresses**

     | Group Command | Bus Principal    | Bus Local      | Note                             | 
     | ------------- | :--------------: | :------------: | -------------------------------- |
     | Area          | `OK`             | `No Test`      |                                  |
     | Group         | `Problem (1)`    | `No Test`      | **`(1)`** OwnId problem creation, see bridgeHandler.ownIdFromWhoWhere().<br>Es. `2.1` instead of `2.#1`   |
     | General       | `OK`             | `No Test`      | **`(1)`** By sending the command, the group and area handlers are also called, `correct ???`                                 |

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



## Group Example

### openwebnet.things:

```xtend
Bridge openwebnet:bus_gateway:mybridge "MyHOMEServer1" [ host="192.168.1.35", passwd="abcde", port=20000, discoveryByActivation=true ]
{  
      bus_on_off_switch MygroupArea    "Group 1-2 Area"		[ where="2", what=0, addrtype=2 ]
      bus_on_off_switch MygroupGen     "Group 1-GEN"		[ where="0", what=0, addrtype=4 ]
}
``` 

### openwebnet.items:

```xtend
Switch   ILR_testArea1   "Group 1-2 AREA"   { channel="openwebnet:bus_on_off_switch:mybridge:MygroupArea:switch"}
Switch   ILR_testGen1    "Group 1-GEN"      { channel="openwebnet:bus_on_off_switch:mybridge:MygroupGen:switch"}

```

### openwebnet.sitemap

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
