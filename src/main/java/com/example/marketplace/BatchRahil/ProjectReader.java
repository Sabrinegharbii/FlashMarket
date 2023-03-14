package com.example.marketplace.BatchRahil;

import com.example.marketplace.entities.Charity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
//@Slf4j
//public class ProjectReader implements ItemReader<ItemReader<Charity>>{
//    @Autowired
//    private ICharityServ charityServ;
//
//
//    /*4. Création des variables de notre batch (nom fichier, nom lecteur ) */
//    private static final String FILE_NAME = "donnation.csv";
//    private static final String READER_NAME = "projectItemReader";
//
//    /*5. Lister les champs que nous souhaitons parser dans le
//     * fichier excel*/
//    private String names = "id,sosName,sosAddress,sosPhone,score,nbre,maladie,CasGrave";
//    /*6. Définir la stratégie de délimitation des différents champs*/
//    private String delimiter = ",";
//
//
//    /*10. étape 1 (ItemReader) Créer le reader pour récupérer les données depuis
//     * le fichier csv*/
//    @Override
//    public ItemReader<Charity> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        log.error("Start Batch Item Reader");
//        FlatFileItemReader<Charity> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource(FILE_NAME));
//        reader.setName(READER_NAME);
//        reader.setLinesToSkip(1);
//        /*11. récupérer les données ligne par ligne du fichier excel */
//        reader.setLineMapper(projectLineMapper());
//
//        return reader;
//    }
//
//    /*11. récupérer les données ligne par ligne du fichier excel */
//    public LineMapper<Charity> projectLineMapper() {
//
//        log.info("Start Batch Line Mapper");
//
//        final DefaultLineMapper<Charity> defaultLineMapper = new DefaultLineMapper<>();
//        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//        lineTokenizer.setDelimiter(delimiter);
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames(names.split(delimiter));
//        defaultLineMapper.setLineTokenizer(lineTokenizer);
//        defaultLineMapper.setFieldSetMapper(fieldSet -> {
//            if (fieldSet.readBoolean(2)) {
//                return charityServ.retrieveCharity(fieldSet.readInt(1));
//
//            }
//            else {
//                return null;
//            }
//
//        });
//        return defaultLineMapper;
//    }
//}
