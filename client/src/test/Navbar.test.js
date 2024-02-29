import React from 'react';
import { shallow } from 'enzyme';
import Navbar from '../components/Navbar/Navbar';

describe('<Navbar />', () => {
  let component;

  beforeEach(() => {
    component = shallow(<Navbar />);
  });

  test('It should mount', () => {
    expect(component.length).toBe(1);
  });
});
