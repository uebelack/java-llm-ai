import {render} from 'preact';
import {useEffect, useRef, useState} from 'preact/hooks';
import {html} from 'htm/preact';

const App = () => {
    const input = useRef(null);
    const messagesElement = useRef(null);
    const [fetching, setFetching] = useState(false);
    const [messages, setMessages] = useState([]);
    const [request, setRequest] = useState('');

    const handleSubmit = async (event) => {
        event.preventDefault();
        console.log(event);
        const formData = new FormData(event.target);
        const request = formData.get('request');
        const newMessages = [...messages, {content: request, role: 'USER'}];
        setMessages(newMessages);

        setFetching(true);

        try {
            const response = await fetch(`/api/ai`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newMessages),
            });

            if (!response?.ok) {
                throw new Error('Error while fetching data');
            }

            const data = await response.json();
            setMessages([...newMessages, data]);
            setFetching(false);
        } catch (e) {
            setMessages([...newMessages, {content: e.toString(), type: 'SYSTEM'}]);
            setFetching(false);
        }
    };

    useEffect(() => {
        if (!fetching) {
            input.current.focus();
        }
    }, [fetching]);

    useEffect(() => {
        messagesElement.current.scrollTo(0, messagesElement.current.scrollHeight)
    }, [messages]);

    return html`
        <main>
            <div class="messages-wrapper">
                <div class="messages" ref=${messagesElement}>
                    ${messages.map((message) => html`
                        <div class=${message.role.toLowerCase()}>${message.content}</div>`)}
                    ${fetching && html`
                        <div class="typing assistant">
                            <div class="typing__dot"></div>
                            <div class="typing__dot"></div>
                            <div class="typing__dot"></div>
                        </div>
                    `}
                </div>
            </div>
            <form onsubmit=${handleSubmit}>
                <input ref=${input} name="request" type="text" autofocus value=${request} disabled=${fetching}></input>
                <button type="submit" disabled=${fetching}>Send</button>
            </form>
        </main>
    `;
};

render(html`
    <${App}/>`, document.body);