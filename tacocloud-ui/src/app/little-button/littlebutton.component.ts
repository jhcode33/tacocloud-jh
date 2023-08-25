import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'little-button',
  templateUrl: 'littlebutton.component.html',
  styleUrls: ['./littlebutton.component.css']
})

export class LittleButtonComponent implements OnInit {

  @Input() label: string | undefined;

  @Output() clickEvent = new EventEmitter<void>();

  constructor() { }

  ngOnInit() { }

  onClick() {
    this.clickEvent.emit();
  }
}
