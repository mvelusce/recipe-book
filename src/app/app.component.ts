import { Component } from '@angular/core';
import { ElectronService } from './providers/electron.service';
import { TranslateService } from '@ngx-translate/core';
import { AppConfig } from '../environments/environment';

import { ImportExportService } from './services/import-export/import-export.service'

import { remote } from 'electron';
let { dialog } = remote;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  constructor(
    public electronService: ElectronService,
    private translate: TranslateService,
    private importExportService: ImportExportService
    ) {

    translate.setDefaultLang('it');
    console.log('AppConfig', AppConfig);

    if (electronService.isElectron()) {
      console.log('Mode electron');
      console.log('Electron ipcRenderer', electronService.ipcRenderer);
      console.log('NodeJS childProcess', electronService.childProcess);
    } else {
      console.log('Mode web');
    }
    
    var menu = remote.Menu.buildFromTemplate([
      {
        label: 'File',
        submenu: [{
          label: 'Import',
          click() {
            dialog.showOpenDialog({
              title: "Select file",
              filters: [{name: '.csv,.txt', extensions: ['csv', 'txt']}],
              properties: ["openFile", "multiSelections", "createDirectory", "promptToCreate"]
            }, (paths, bookmarks) => {
              paths.forEach((v, i, a) => importExportService.importRecipes(v));
            })
          } 
        },
        {
          label: 'Export',
          click() {
            dialog.showSaveDialog({
              title: "Select file",
              filters: [{name: '.csv,.txt', extensions: ['csv', 'txt']}]
            }, (filename, bookmark) => {
              importExportService.exportRecipes(filename);
            })
          } 
        }]
      },
      {
        label: 'Edit',
        submenu: [
          {role: 'undo'},
          {role: 'redo'},
          {type: 'separator'},
          {role: 'cut'},
          {role: 'copy'},
          {role: 'paste'},
          {role: 'pasteandmatchstyle'},
          {role: 'delete'},
          {role: 'selectall'}
        ]
      },
      {
        label: 'View',
        submenu: [
          {role: 'reload'},
          {role: 'forcereload'},
          {role: 'toggledevtools'},
          {type: 'separator'},
          {role: 'resetzoom'},
          {role: 'zoomin'},
          {role: 'zoomout'},
          {type: 'separator'},
          {role: 'togglefullscreen'}
        ]
      },
      {
        role: 'window',
        submenu: [
          {role: 'minimize'},
          {role: 'close'}
        ]
      },
      {
        role: 'help',
        submenu: [
          {
            label: 'Learn More',
            click () { require('electron').shell.openExternal('https://electronjs.org') }
          }
        ]
      }
    ])
    remote.Menu.setApplicationMenu(menu);
  }
}
