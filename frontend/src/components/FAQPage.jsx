import React, { useState } from 'react';
import { Box, Container, Typography, Collapse } from '@mui/material';

const faqData = [
    {
        question: 'Как изменить аватар профиля??',
        answer: 'Перейдите в настройки профиля, загрузите новое изображение и сохраните изменения..',
    },
    {
        question:'Как изменить страну профиля?',
        answer: 'Откройте настройки профиля, найдите раздел с информацией о стране, выберите нужное значение из списка и сохраните изменения.',
    },
    {
        question: 'Как поменять пароль?',
        answer: 'В окне редактирования профиля найдите раздел «Смена пароля», введите текущий пароль, затем новый и подтвердите его, после чего сохраните изменения.',
    },
    {
        question: 'Как загрузить забег?',
        answer: 'Перейдите в вкладку игры, Нажмите на кнопку загрузки, заполните необходимые поля и отправьте запрос.',
    },
    {
        question: 'Где посмотреть правила категории?',
        answer: 'Правила категории можно найти в описании соответствующей игры на её странице.\n.',
    },
    {
        question: 'Можно ли подать апелляцию на бан?',
        answer: 'Да, вы можете написать администрации и изложить вашу позицию. Решение принимается индивидуально.',
    },
    {
        question: 'Как найти нужную игру?',
        answer: 'Используйте поисковую строку сайта для быстрого поиска нужной игры..',
    },
];

function FAQItem({ question, answer }) {
    const [open, setOpen] = useState(false);

    const handleToggle = () => {
        setOpen((prev) => !prev);
    };

    return (
        <Box sx={{ mb: 2 }}>
            <Box
                onClick={handleToggle}
                sx={{
                    backgroundColor: open ? '#C0AA94' : '#D9D9D9',
                    color: '#3F3429',
                    border: '5px solid',
                    borderColor: open ? 'var(--Border-Warning-Secondary, #975102)' : 'transparent',
                    padding: '16px',
                    cursor: 'pointer',
                    transform: open ? 'translateX(-20px)' : 'none',
                    transition: 'background-color 0.3s, transform 0.3s, border-color 0.3s',
                }}
            >
                <Typography variant="subtitle1" fontWeight="bold">
                    {question}
                </Typography>
            </Box>
            <Collapse in={open} timeout="auto">
                <Box
                    sx={{
                        backgroundColor: '#DBE7FF',
                        border: '5px solid #72A1FF',
                        color: '#3F3429',
                        padding: '16px',
                        mt: 1,
                        transform: 'translateX(20px)',
                        transition: 'transform 0.3s',
                    }}
                >
                    <Typography>{answer}</Typography>
                </Box>
            </Collapse>
        </Box>
    );
}

function FAQPage() {
    return (
        <Box
            sx={{
                minHeight: '100vh',
                backgroundImage: `url('/assets/mascots/faqMacote1.png'), url('/assets/mascots/faqMacote2.png')`,
                backgroundPosition: 'left center, right center',
                backgroundRepeat: 'no-repeat, no-repeat',
                backgroundAttachment: 'scroll, scroll',
                py: 4,
            }}
        >
            <Container maxWidth="md">
                <Typography variant="h4" align="center" gutterBottom>
                    Часто задаваемые вопросы
                </Typography>
                {faqData.map((item, index) => (
                    <FAQItem key={index} question={item.question} answer={item.answer} />
                ))}
            </Container>
        </Box>
    );
}

export default FAQPage;
