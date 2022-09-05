import 'bootstrap/dist/css/bootstrap.min.css';
import{Nav, Navbar, Button} from "react-bootstrap";
import { AlpheccaLogo} from './Common';


export default function NavBarcomp(){
    var notLogin = false;
    return(
        <Navbar bg='dark' variant='dark' sticky='top' expand='sm' collapseOnSelect>
            <Navbar.Brand href='/'>
                <AlpheccaLogo />
            </Navbar.Brand>
            <Navbar.Toggle/>
            <Navbar.Collapse>
            <Nav>
                <Nav.Link href="/about">
                    About Us
                </Nav.Link>
                <Nav.Link href="/contact">
                    Contact
                </Nav.Link>
               
                {notLogin&&<Button href="/login">
                    Login
                </Button>}
            </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
}
