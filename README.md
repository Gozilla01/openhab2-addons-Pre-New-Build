# openhab2-addons-Pre-New-Build

## Changelog

**v2.5.0.M4.pre1 =IN PROGRESS=** - 08/06/2019

- From [v2.5.0.M3](https://github.com/mvalla/openhab2-addons/blob/openwebnet/bundles/org.openhab.binding.openwebnet/README_beta.md#changelog)
- [FIX [#80](https://github.com/mvalla/openhab2-addons/issues/80)] thermo: setmode in MANUAL not work
- [FIX [#12](https://github.com/mvalla/openhab2-addons/issues/12) and [#32](https://github.com/mvalla/openhab2-addons/issues/32)] Add bus AUX
   - **TO DO** file update README.MD
- [FIX [#63](https://github.com/mvalla/openhab2-addons/issues/63)] Rollershutter items do not track changes in movement from external commands
   - *[BREAKING CHANGE]* canale `shutter` changed the name to `shutterPosition`
   - New channel `shutterMotion` (`0`= stop, `1`= up, `2`= down) 
   - **TO DO** file update README.MD
- [Fix [#69](https://github.com/mvalla/openhab2-addons/issues/69)] Update channel states with group commands
