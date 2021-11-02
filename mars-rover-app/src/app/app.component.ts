import {Component, OnInit} from '@angular/core';
import {AdminService} from './shared/rest/admin.service';
import {PlateauCoord} from './shared/model/plateauCoord';
import {RoverCommand} from './shared/model/roverCommand';
import {RoverDetail} from './shared/model/roverDetail';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  plateauLabel = 'Plateau Detail';
  roverLabel = 'Rover Commands';
  roverMoveLabel = 'Rover(s) New Position';
  buttonRoverLabel = 'Trigger Command';
  buttonPlateauLabel = 'Set Plateau';
  plateauPosition: string;
  initiateProject: boolean;
  enableRoverInput: boolean;
  initiateRoverCommand: boolean;
  roverCommand: string;
  plateauCoord: PlateauCoord = new PlateauCoord();
  roverCommandSet: RoverCommand = new RoverCommand();
  plateauId: string;
  showInfo: boolean;
  showGrid: boolean;
  plateauErr: boolean;
  roverErr: boolean;
  roverDetails: RoverDetail[];

  constructor(private adminService: AdminService) {
  }

  ngOnInit(): void {
    this.initiateProject = false;
    this.enableRoverInput = false;
    this.initiateRoverCommand = false;
    this.showGrid = false;
    this.plateauErr = false;
    this.showInfo = true;
    this.roverErr = false;
  }

  createPlateau(plateauPosition: string): boolean {
    if (this.initiateProject === false) {
      if (plateauPosition.length > 0) {
        const myPlateau = this.buildPlateauCoord(plateauPosition);
        if (myPlateau !== null) {
          this.adminService.savePlateauDetails(this.buildPlateauCoord(plateauPosition)).subscribe(
            response => {
              console.log(response);
              if (response !== null) {
                this.plateauErr = false;
                this.initiateProject = true;
                this.plateauId = response.id;
                this.enableRoverInput = true;
                return true;
              }
            }
          );
        }
        this.plateauErr = true;
        this.enableRoverInput = false;
        this.initiateProject = false;
        return false;
      }

    }
    return false;
  }

  triggerRoverCommand(roverCommand: string): void {
    if (this.initiateRoverCommand === false) {
      this.initiateRoverCommand = true;
      const buildResult = this.buildRoverCommand(roverCommand);
      if (buildResult !== null) {
        this.adminService.updatePlateauDetails(buildResult).subscribe(
          response => {
            if (response.allRovers.length > 0) {
              this.roverDetails = response.allRovers;
              this.enableRoverInput = false;
              this.roverErr = false;
            } else {
              this.roverErr = true;
            }
          }
        );
      } else {
        this.roverErr = true;
      }

    }
  }

  buildRoverCommand(roverCommands: string): any {
    let roverCoord;
    let roverControl;
    if (roverCommands) {
      this.roverCommandSet.id = this.plateauId;
      const roverCommand = roverCommands.split('\n');
      const sizeRoverCommand = roverCommand.length;
      for (let i = 0; i < sizeRoverCommand; i++) {
        roverCoord = roverCommand[i].substring(0, 5);
        roverControl = roverCommand[i].substring(5);
        if (this.isCorrectCoord(roverCoord) && this.isAlpha(roverControl)) {
          this.roverCommandSet.roverRequestDetails += roverCoord + '}' + roverControl + ']';
          return this.roverCommandSet;
        } else {
          return null;
        }
      }
    }
  }

  buildPlateauCoord(plateau: string): any {
    if (plateau && plateau.length > 0) {
      const temp = plateau.split(',');
      if (temp.length > 0) {
        if (temp[0].indexOf(' ')) {
          temp[0] = temp[0].replace(/'  '/g, '');
        }
        if (temp[1].indexOf(' ')) {
          temp[1] = temp[1].replace(/'  '/g, '');
        }
        this.plateauCoord.maxXCoordinate = temp[0];
        this.plateauCoord.maxYCoordinate = temp[1];
        return this.plateauCoord;
      }
    }
    this.plateauCoord.maxXCoordinate = '';
    this.plateauCoord.maxYCoordinate = '';
    return this.plateauCoord;
  }

  isNumeric(input: string) {
    return (input.toUpperCase().match('^[0-9,]') !== null);
  }

  isAlphaNumeric(input: string) {
    return (input.match('^[A-Za-z0-9]') !== null);
  }

  isCorrectCoord(input: string) {
    return (input.match('^[0-9NSWE ]') !== null);
  }

  isAlpha(input: string) {
    return (input.match('^[LMR]') !== null);
  }

  isCorrectChar(input: string) {
    return (input.match('^[LMRENWS0-9 ]') !== null);
  }

  showInfoText(value: boolean): void {
    this.showInfo = !value;
  }
}
